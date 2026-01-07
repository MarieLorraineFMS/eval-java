package fr.fms.service;

import static fr.fms.utils.Helpers.isNullOrEmpty;

import java.util.List;

import fr.fms.dao.TrainingDao;
import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.Training;

public class TrainingService {
    private final TrainingDao trainingDao;

    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    // Public catalog
    public List<Training> listAll() {
        return trainingDao.findAll();
    }

    // Search by keyword
    public List<Training> searchByKeyword(String keyword) {
        // Clean user input
        String kw = isNullOrEmpty(keyword) ? "" : keyword.trim();

        // Empty keyword => show all trainings
        if (isNullOrEmpty(kw)) {
            return trainingDao.findAll();
        }

        // Normalize case
        return trainingDao.searchByKeyword(kw.toLowerCase());
    }

    // Filter by onsite/distanciel
    public List<Training> listByOnsite(boolean onsite) {
        return trainingDao.findByOnsite(onsite);
    }

    public Training getById(int trainingId) {
        if (trainingId <= 0) {
            throw new IllegalArgumentException("Training id must be > 0.");
        }

        return trainingDao.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException("Training not found: id=" + trainingId));
    }
}
