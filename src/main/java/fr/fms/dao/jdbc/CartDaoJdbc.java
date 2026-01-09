package fr.fms.dao.jdbc;

import static fr.fms.utils.Helpers.toLdt;

import java.math.BigDecimal;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.CartDao;
import fr.fms.exception.DaoException;
import fr.fms.model.Cart;
import fr.fms.model.CartItem;
import fr.fms.model.Training;

/**
 * JDBC implementation of {@link CartDao}.
 *
 * Responsibilities:
 * - Load a cart for a user (cart header + cart items)
 * - Create cart if missing (get-or-create pattern)
 * - Update cart items (add/increment, decrement/remove, delete line, clear)
 *
 */
public class CartDaoJdbc implements CartDao {

    /**
     * Finds a cart for a given user id.
     * If found, also loads cart items via a second query.
     *
     * @param userId identifier of the user
     * @return an Optional containing the Cart if it exists, otherwise
     *         Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<Cart> findByUserId(int userId) {
        final String cartSql = "SELECT id, user_id, created_at FROM cart WHERE user_id = ?";

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(cartSql)) {

            ps.setInt(1, userId);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();

                int cartId = rs.getInt("id");
                LocalDateTime createdAt = toLdt(rs.getTimestamp("created_at"));

                // Load items in a separate query (because one query is never enough)
                var items = loadItemsByCartId(cnx, cartId);

                return Optional.of(new Cart(cartId, userId, createdAt, items));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find cart by userId=" + userId, e);
        }
    }

    /**
     * Returns an existing cart id for the user, or creates a cart & returns its
     * id.
     *
     * Notes:
     * - It tries SELECT first (cheap).
     * - If not found, it tries INSERT.
     * - If a concurrent insert happens, it re-selects ("race condition"
     * defense).
     *
     * @param userId identifier of the user
     * @return cart id, 0 if insert failed.
     * @throws DaoException if a db error occurs
     */
    @Override
    public int getOrCreateCartId(int userId) {
        // Safe even if two calls happen at the same time
        final String select = "SELECT id FROM cart WHERE user_id = ?";
        final String insert = "INSERT INTO cart(user_id) VALUES(?)";

        try (var cnx = DbConfig.getConnection()) {

            // Try find first
            Integer existing = selectCartId(cnx, select, userId);
            if (existing != null) {
                return existing;
            }

            // Try create
            try (var ps = cnx.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);

                int affected = ps.executeUpdate();
                if (affected == 0)
                    return 0;

                try (var keys = ps.getGeneratedKeys()) {
                    if (!keys.next())
                        return 0;
                    return keys.getInt(1);
                }
            } catch (Exception duplicateOrOther) {
                // If it's a duplicate key, re-select and move on like nothing happened
                Integer after = selectCartId(cnx, select, userId);
                if (after != null) {
                    return after;
                }
                throw duplicateOrOther;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to getOrCreateCartId for userId=" + userId, e);
        }
    }

    /**
     * Selects the cart id for a user.
     *
     * @param cnx       opened db connection
     * @param selectSql SQL query used to select cart id by user id
     * @param userId    identifier of the user
     * @return cart id if found, otherwise null
     * @throws Exception if a db error occurs
     */
    private Integer selectCartId(java.sql.Connection cnx, String selectSql, int userId) throws Exception {
        try (var ps = cnx.prepareStatement(selectSql)) {
            ps.setInt(1, userId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return null;
            }
        }
    }

    /**
     * Adds an item to the cart or increments quantity if the line already exists.
     *
     * Uses a db upsert:
     * - INSERT if not present
     * - UPDATE (quantity = quantity + delta) if already present
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @param deltaQty   quantity to add
     * @param unitPrice  unit price
     * @throws DaoException if a db error occurs
     */
    @Override
    public void addOrIncrement(int cartId, int trainingId, int deltaQty, BigDecimal unitPrice) {
        final String upsert = """
                INSERT INTO cart_item(cart_id, training_id, quantity, unit_price)
                VALUES(?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    quantity = quantity + VALUES(quantity),
                    unit_price = VALUES(unit_price)
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(upsert)) {

            ps.setInt(1, cartId);
            ps.setInt(2, trainingId);
            ps.setInt(3, deltaQty);
            ps.setBigDecimal(4, unitPrice);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DaoException("Failed to addOrIncrement cartId=" + cartId + " trainingId=" + trainingId, e);
        }
    }

    /**
     * Decrements quantity for a cart line or deletes the line if quantity becomes<=
     * 0.
     *
     * Transactional:
     * - Step 1: UPDATE quantity
     * - Step 2: DELETE if quantity <= 0
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @param deltaQty   quantity to remove (must be positive, we subtract it)
     * @return true if the line existed & was updated, false if no update
     * @throws DaoException if a db error occurs
     */
    @Override
    public boolean decrementOrRemove(int cartId, int trainingId, int deltaQty) {
        // Step 1: decrement
        final String dec = """
                UPDATE cart_item
                SET quantity = quantity - ?
                WHERE cart_id = ? AND training_id = ?
                """;

        // Step 2: delete if <= 0
        final String del = """
                DELETE FROM cart_item
                WHERE cart_id = ? AND training_id = ? AND quantity <= 0
                """;

        try (var cnx = DbConfig.getConnection()) {
            cnx.setAutoCommit(false);

            try {
                int updated;
                try (var ps = cnx.prepareStatement(dec)) {
                    ps.setInt(1, deltaQty);
                    ps.setInt(2, cartId);
                    ps.setInt(3, trainingId);
                    updated = ps.executeUpdate();
                }

                // If nothing was updated, the item probably does not exist
                if (updated == 0) {
                    cnx.rollback();
                    return false;
                }

                // Cleanup: remove line if quantity is now <= 0
                try (var ps = cnx.prepareStatement(del)) {
                    ps.setInt(1, cartId);
                    ps.setInt(2, trainingId);
                    ps.executeUpdate();
                }

                cnx.commit();
                return true;

            } catch (Exception e) {
                try {
                    cnx.rollback();
                } catch (Exception ignored) {
                    // If rollback fails, we are already in trouble territory
                }
                throw e;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to decrementOrRemove cartId=" + cartId + " trainingId=" + trainingId, e);
        }
    }

    /**
     * Removes an entire line from the cart.
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @return true if a row was deleted, false if the line did not exist
     * @throws DaoException if a db error occurs
     */
    @Override
    public boolean removeLine(int cartId, int trainingId) {
        final String sql = "DELETE FROM cart_item WHERE cart_id = ? AND training_id = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, trainingId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new DaoException("Failed to removeLine cartId=" + cartId + " trainingId=" + trainingId, e);
        }
    }

    /**
     * Clears all items for a given cart.
     *
     * @param cartId identifier of the cart
     * @throws DaoException if a db error occurs
     */
    @Override
    public void clear(int cartId) {
        final String sql = "DELETE FROM cart_item WHERE cart_id = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new DaoException("Failed to clear cartId=" + cartId, e);
        }
    }

    /**
     * Loads all cart items for a given cart id.
     *
     * It joins training to build full CartItem objects.
     *
     * @param cnx    open db connection
     * @param cartId identifier of the cart
     * @return list of CartItem for the cart
     * @throws Exception if a db error occurs
     */
    private ArrayList<CartItem> loadItemsByCartId(java.sql.Connection cnx, int cartId) throws Exception {
        final String sql = """
                SELECT
                    ci.id as cart_item_id,
                    ci.cart_id,
                    ci.quantity,
                    ci.unit_price,
                    t.id as training_id,
                    t.name,
                    t.description,
                    t.duration_days,
                    t.price,
                    t.onsite
                FROM cart_item ci
                JOIN training t ON t.id = ci.training_id
                WHERE ci.cart_id = ?
                ORDER BY ci.id
                """;

        try (var ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, cartId);

            try (var rs = ps.executeQuery()) {
                ArrayList<CartItem> items = new ArrayList<>();
                while (rs.next()) {
                    // Build Training object
                    Training t = new Training(
                            rs.getInt("training_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("duration_days"),
                            rs.getBigDecimal("price"),
                            rs.getBoolean("onsite"));

                    // Build CartItem object
                    CartItem item = new CartItem(
                            rs.getInt("cart_item_id"),
                            rs.getInt("cart_id"),
                            t,
                            rs.getInt("quantity"),
                            rs.getBigDecimal("unit_price"));

                    items.add(item);
                }
                return items;
            }
        }
    }

}
