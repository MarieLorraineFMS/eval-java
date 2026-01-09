package fr.fms.dao.jdbc;

import static fr.fms.utils.Helpers.toLdt;

import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.OrderDao;
import fr.fms.exception.DaoException;
import fr.fms.model.Client;
import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.model.OrderStatus;
import fr.fms.model.Training;
import fr.fms.utils.AppLogger;

/**
 * JDBC implementation of {@link OrderDao}.
 *
 * Handles:
 * - inserting orders
 * - reading orders
 * - updating order status
 *
 * Note: name `order` is reserved in SQL, so backticks are required.
 * SQL... always trying to be special
 */
public class OrderDaoJdbc implements OrderDao {

    /**
     * Creates an order header in db (no lines).
     *
     * @param order order header to persist
     * @return generated order id, or 0 if creation failed
     * @throws DaoException if a db error occurs
     */
    @Override
    public int create(Order order) {
        // Backticks mandatory : order is a reserved word
        final String sql = """
                INSERT INTO `order`(user_id, client_id, created_at, status, total)
                VALUES(?, ?, ?, ?, ?)
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getClient().getId());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
            ps.setString(4, order.getStatus().toDb());
            ps.setBigDecimal(5, order.getTotal());

            int affected = ps.executeUpdate();
            if (affected == 0)
                return 0;

            try (var keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    return 0;
                return keys.getInt(1);
            }

        } catch (Exception e) {
            throw new DaoException("Failed to create order for userId=" + order.getUserId(), e);
        }
    }

    /**
     * Creates an order header & its lines in a single transaction.
     * All-or-nothing: if anything fails, rollback is triggered.
     *
     * @param order order header to persist
     * @param lines order lines to persist
     * @return generated order id, or 0 if creation failed
     * @throws DaoException if a db error occurs
     */
    @Override
    public int createOrderWithLines(Order order, List<OrderLine> lines) {
        // ONE transaction => header & lines => all or nothing
        final String insertOrderSql = """
                INSERT INTO `order`(user_id, client_id, created_at, status, total)
                VALUES(?, ?, ?, ?, ?)
                """;

        final String insertLineSql = """
                INSERT INTO order_line(order_id, training_id, quantity, unit_price)
                VALUES(?, ?, ?, ?)
                """;

        try (var cnx = DbConfig.getConnection()) {
            AppLogger.info("Create order with lines for orderId=" + order.getId());

            cnx.setAutoCommit(false);

            try {
                int orderId = insertOrderHeader(cnx, insertOrderSql, order);
                if (orderId <= 0) {
                    cnx.rollback();
                    return 0;
                }

                insertOrderLines(cnx, insertLineSql, orderId, lines);

                cnx.commit();
                return orderId;

            } catch (Exception e) {
                try {
                    cnx.rollback();
                } catch (Exception ignored) {
                    // At this point, we did our best
                }
                throw e;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to create order with lines (transaction)", e);
        }
    }

    /**
     * Inserts the order header & returns the generated order id.
     *
     * @param cnx   open db connection
     * @param sql   insert SQL for the order header
     * @param order order header data
     * @return generated order id, or 0 if insertion failed
     * @throws Exception if a db error occurs
     */
    private int insertOrderHeader(java.sql.Connection cnx, String sql, Order order) throws Exception {
        try (var ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getClient().getId());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
            ps.setString(4, order.getStatus().toDb());
            ps.setBigDecimal(5, order.getTotal());

            int affected = ps.executeUpdate();
            if (affected == 0)
                return 0;

            try (var keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    return 0;
                return keys.getInt(1);
            }
        }
    }

    /**
     * Inserts order lines using a batch.
     *
     * @param cnx     open db connection
     * @param sql     insert SQL for order lines
     * @param orderId identifier of the created order
     * @param lines   list of lines to insert
     * @throws Exception if a db error occurs
     */
    private void insertOrderLines(java.sql.Connection cnx, String sql, int orderId, List<OrderLine> lines)
            throws Exception {

        // Batch is easier to keep clean with a simple loop
        try (var ps = cnx.prepareStatement(sql)) {
            for (OrderLine line : lines) {
                ps.setInt(1, orderId);
                ps.setInt(2, line.getTraining().getId());
                ps.setInt(3, line.getQuantity());
                ps.setBigDecimal(4, line.getUnitPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     * Adds one order line to an existing order.
     *
     * @param orderId    identifier of the order
     * @param trainingId identifier of the training
     * @param quantity   quantity purchased
     * @param unitPrice  unit price
     * @throws DaoException if a db error occurs
     */
    @Override
    public void addLine(int orderId, int trainingId, int quantity, BigDecimal unitPrice) {
        final String sql = """
                INSERT INTO order_line(order_id, training_id, quantity, unit_price)
                VALUES(?, ?, ?, ?)
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, trainingId);
            ps.setInt(3, quantity);
            ps.setBigDecimal(4, unitPrice);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DaoException("Failed to add order line orderId=" + orderId + " trainingId=" + trainingId, e);
        }
    }

    /**
     * Finds an order by id, including:
     * - order header
     * - client data
     * - order lines
     *
     * @param orderId identifier of the order
     * @return Optional containing the Order if found, otherwise Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<Order> findById(int orderId) {
        final String sql = """
                SELECT
                    o.id as order_id,
                    o.user_id,
                    o.client_id,
                    o.created_at,
                    o.status,
                    o.total,
                    c.id as c_id,
                    c.first_name,
                    c.last_name,
                    c.email,
                    c.address,
                    c.phone
                FROM `order` o
                JOIN client c ON c.id = o.client_id
                WHERE o.id = ?
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();

                Order order = mapOrderHeader(rs);

                List<OrderLine> lines = loadLines(cnx, orderId);
                lines.forEach(order::addLine);

                return Optional.of(order);
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find order by id=" + orderId, e);
        }
    }

    /**
     * Finds all orders for a given user id.
     * Each order is returned with:
     * - client data
     * - order lines
     *
     * @param userId identifier of the user
     * @return list of orders ordered
     * @throws DaoException if a db error occurs
     */
    @Override
    public List<Order> findByUserId(int userId) {
        final String sql = """
                SELECT
                    o.id as order_id,
                    o.user_id,
                    o.client_id,
                    o.created_at,
                    o.status,
                    o.total,
                    c.id as c_id,
                    c.first_name,
                    c.last_name,
                    c.email,
                    c.address,
                    c.phone
                FROM `order` o
                JOIN client c ON c.id = o.client_id
                WHERE o.user_id = ?
                ORDER BY o.id DESC
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (var rs = ps.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order o = mapOrderHeader(rs);

                    List<OrderLine> lines = loadLines(cnx, o.getId());
                    lines.forEach(o::addLine);

                    orders.add(o);
                }
                return orders;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find orders by userId=" + userId, e);
        }
    }

    /**
     * Maps the "order header + client columns" from ResultSet into an Order object.
     *
     * @param rs SQL result set
     * @return mapped Order object
     * @throws Exception if a SQL access error occurs
     */
    private Order mapOrderHeader(java.sql.ResultSet rs) throws Exception {
        int orderId = rs.getInt("order_id");
        int userId = rs.getInt("user_id");

        Client client = new Client(
                rs.getInt("c_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("phone"));

        LocalDateTime createdAt = toLdt(rs.getTimestamp("created_at"));
        OrderStatus status = OrderStatus.fromDb(rs.getString("status"));
        BigDecimal total = rs.getBigDecimal("total");

        return new Order(orderId, userId, client, createdAt, status, total);
    }

    /**
     * Loads all lines for a given order id.
     * It joins training to build full OrderLine objects.
     *
     * @param cnx     open db connection (
     * @param orderId identifier of the order
     * @return list of OrderLine for the order
     * @throws Exception if a db error occurs
     */
    private List<OrderLine> loadLines(java.sql.Connection cnx, int orderId) throws Exception {
        final String sql = """
                SELECT
                    ol.id as line_id,
                    ol.order_id,
                    ol.training_id,
                    ol.quantity,
                    ol.unit_price,
                    t.name,
                    t.description,
                    t.duration_days,
                    t.price,
                    t.onsite
                FROM order_line ol
                JOIN training t ON t.id = ol.training_id
                WHERE ol.order_id = ?
                ORDER BY ol.id
                """;

        try (var ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            try (var rs = ps.executeQuery()) {
                List<OrderLine> lines = new ArrayList<>();
                while (rs.next()) {
                    Training t = new Training(
                            rs.getInt("training_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("duration_days"),
                            rs.getBigDecimal("price"),
                            rs.getBoolean("onsite"));

                    OrderLine line = new OrderLine(
                            rs.getInt("line_id"),
                            rs.getInt("order_id"),
                            t,
                            rs.getInt("quantity"),
                            rs.getBigDecimal("unit_price"));

                    lines.add(line);
                }
                return lines;
            }
        }
    }

    /**
     * Updates status of an order.
     *
     * @param orderId identifier of the order
     * @param status  new status
     * @return true if an order was updated, false otherwise
     * @throws DaoException if a db error occurs
     */
    @Override
    public boolean updateStatus(int orderId, OrderStatus status) {
        final String sql = "UPDATE `order` SET status = ? WHERE id = ?";

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setString(1, status.toDb());
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new DaoException("Failed to update status for orderId=" + orderId, e);
        }
    }
}
