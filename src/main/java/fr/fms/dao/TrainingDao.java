package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Training;

/**
 * Data Access Object interface for Training.
 *
 * Defines read operations for trainings:
 * - list all trainings
 * - find by id
 * - search by keyword (name/description)
 * - filter by onsite flag
 *
 * JDBC handle db details.
 */
public interface TrainingDao {

    /**
     * Retrieves all trainings.
     *
     * @return list of all trainings
     */
    List<Training> findAll();

    /**
     * Finds a training by its identifier.
     *
     * @param id training identifier
     * @return Optional containing the Training if found, otherwise Optional.empty()
     */
    Optional<Training> findById(int id);

    /**
     * Searches trainings by keyword in name & description.
     *
     * @param keyword keyword to search
     * @return list of trainings matching keyword
     */
    List<Training> searchByKeyword(String keyword);

    /**
     * Retrieves trainings filtered by onsite flag.
     *
     * @param onsite true for onsite trainings, false for remote trainings
     * @return list of trainings matching the onsite filter
     */
    List<Training> findByOnsite(boolean onsite);
}
