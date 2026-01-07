package fr.fms.dao.jdbc;

import static fr.fms.utils.Helpers.isNullOrEmpty;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.TrainingDao;
import fr.fms.exception.DaoException;
import fr.fms.model.Training;

public class TrainingDaoJdbc implements TrainingDao {

    @Override
    public List<Training> findAll() {
        final String sql = "SELECT id, name, description, duration_days, price, onsite FROM training ORDER BY id";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql);
                var rs = ps.executeQuery()) {

            List<Training> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapTraining(rs));
            }
            return list;

        } catch (Exception e) {
            throw new DaoException("Failed to find all trainings", e);
        }
    }

    @Override
    public Optional<Training> findById(int id) {
        final String sql = "SELECT id, name, description, duration_days, price, onsite FROM training WHERE id = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapTraining(rs));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find training by id=" + id, e);
        }
    }

    @Override
    public List<Training> searchByKeyword(String keyword) {
        // Search in name & description
        final String sql = """
                SELECT id, name, description, duration_days, price, onsite
                FROM training
                WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?
                ORDER BY id
                """;

        String k = isNullOrEmpty(keyword) ? "" : keyword.trim().toLowerCase();
        String like = "%" + k + "%";

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setString(1, like);
            ps.setString(2, like);

            try (var rs = ps.executeQuery()) {
                List<Training> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapTraining(rs));
                }
                return list;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to search trainings by keyword=" + keyword, e);
        }
    }

    @Override
    public List<Training> findByOnsite(boolean onsite) {
        final String sql = """
                SELECT id, name, description, duration_days, price, onsite
                FROM training
                WHERE onsite = ?
                ORDER BY id
                """;

        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setBoolean(1, onsite);

            try (var rs = ps.executeQuery()) {
                List<Training> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapTraining(rs));
                }
                return list;
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find trainings by onsite=" + onsite, e);
        }
    }

    private Training mapTraining(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String desc = rs.getString("description");
        int duration = rs.getInt("duration_days");
        BigDecimal price = rs.getBigDecimal("price");
        boolean onsite = rs.getBoolean("onsite");
        return new Training(id, name, desc, duration, price, onsite);
    }
}
