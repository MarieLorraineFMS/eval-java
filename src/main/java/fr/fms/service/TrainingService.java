package fr.fms.service;

import fr.fms.model.Training;

import java.util.List;

public interface TrainingService {
    List<Training> listAllTrainings();

    List<Training> searchTrainingsByKeyword(String keyword);

    List<Training> filterTrainingsByModality(boolean onsite);
}
