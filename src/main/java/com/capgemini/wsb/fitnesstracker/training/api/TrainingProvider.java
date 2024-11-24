package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the training with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Retrieves all trainings from the repository.
     *
     * @return List of all trainings.
     */
    List<Training> findAllTrainings();

    List<Training> findTrainingsForUserWithId(Long userId);

    List<Training> findTrainingsEndedAfter(Date time);

    List<Training> findTrainingsByActivityType(ActivityType activityType);

    List<Training> findByUserIdFromLastMonth(Long userId, Date startOfLastMonth, Date endOfLastMonth);

}
