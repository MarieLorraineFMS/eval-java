package fr.fms.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.CartDao;
import fr.fms.exception.DaoException;
import fr.fms.model.Cart;
import fr.fms.model.CartItem;
import fr.fms.model.Training;

public class CartDaoJdbc implements CartDao {

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

                // Load items in a separate query
                var items = loadItemsByCartId(cnx, cartId);

                return Optional.of(new Cart(cartId, userId, createdAt, items));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find cart by userId=" + userId, e);
        }
    }

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
                // If it's a duplicate key re-select
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

                if (updated == 0) {
                    cnx.rollback();
                    return false;
                }

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
                }
                throw e;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to decrementOrRemove cartId=" + cartId + " trainingId=" + trainingId, e);
        }
    }

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
                    Training t = new Training(
                            rs.getInt("training_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("duration_days"),
                            rs.getBigDecimal("price"),
                            rs.getBoolean("onsite"));

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

    private LocalDateTime toLdt(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }
}
