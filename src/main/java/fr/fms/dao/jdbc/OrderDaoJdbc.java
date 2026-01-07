package fr.fms.dao.jdbc;

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

public class OrderDaoJdbc implements OrderDao {

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
                    // Nothing to do...!!!
                }
                throw e;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to create order with lines (transaction)", e);
        }
    }

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

    private void insertOrderLines(java.sql.Connection cnx, String sql, int orderId, List<OrderLine> lines)
            throws Exception {

        // Batch is easier to keep clean with a simple loop (less lambda pain)
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

    private LocalDateTime toLdt(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

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
