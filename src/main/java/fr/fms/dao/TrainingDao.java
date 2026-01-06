package fr.fms.dao;

import fr.fms.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingDao {
    List<Training> readAll();

    List<Training> readByKeyword(String keyword);

    Optional<Training> read(int trainingId);

    List<Training> readByOnsite(boolean onsite);
}