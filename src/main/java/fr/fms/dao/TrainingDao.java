package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Training;

public interface TrainingDao {
    List<Training> findAll();

    Optional<Training> findById(int id);

    List<Training> searchByKeyword(String keyword);

    List<Training> findByOnsite(boolean onsite);
}
