package fr.fms.service;

import static fr.fms.utils.Helpers.isNullOrEmpty;

import java.util.List;

import fr.fms.dao.TrainingDao;
import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.Training;

/**
 * Training service.
 *
 * Responsibilities:
 * - expose the training catalog to the application
 * - validate inputs coming from UI
 * - normalize search/filter behavior
 *
 */
public class TrainingService {

    /** DAO used to access training persistence */
    private final TrainingDao trainingDao;

    /**
     * Builds TrainingService with required dependency.
     *
     * @param trainingDao DAO used to access trainings
     */
    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    /**
     * Returns the full training catalog.
     *
     * @return list of all trainings
     */
    public List<Training> listAll() {
        return trainingDao.findAll();
    }

    /**
     * Searches trainings by keyword.
     *
     * Behavior:
     * - null or empty keyword => return all trainings
     * - keyword is trimmed and normalized to lowercase
     *
     * @param keyword keyword to search
     * @return list of trainings matching the keyword
     */
    public List<Training> searchByKeyword(String keyword) {
        // Clean user input
        String kw = isNullOrEmpty(keyword) ? "" : keyword.trim();

        // Empty keyword => show all trainings
        if (isNullOrEmpty(kw)) {
            return trainingDao.findAll();
        }

        // Normalize case for consistent search
        return trainingDao.searchByKeyword(kw.toLowerCase());
    }

    /**
     * Filters trainings by onsite flag.
     *
     * @param onsite true for onsite trainings, false for remote trainings
     * @return list of trainings matching the filter
     */
    public List<Training> listByOnsite(boolean onsite) {
        return trainingDao.findByOnsite(onsite);
    }

    /**
     * Retrieves a training by its identifier.
     *
     * @param trainingId training identifier
     * @return Training if found
     * @throws IllegalArgumentException  if trainingId <= 0
     * @throws TrainingNotFoundException if training does not exist
     */
    public Training getById(int trainingId) {
        if (trainingId <= 0) {
            throw new IllegalArgumentException("Training id must be > 0.");
        }

        return trainingDao.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(
                        "Training not found: id=" + trainingId));
    }
}
