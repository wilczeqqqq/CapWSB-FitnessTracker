package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface for providing training-related operations.
 */
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

    /**
     * Retrieves all trainings for a specific user by user ID.
     *
     * @param userId the ID of the user.
     * @return list of trainings for the specified user.
     */
    List<Training> findTrainingsForUserWithId(Long userId);

    /**
     * Retrieves all trainings that ended after a specific time.
     *
     * @param time the time to compare against.
     * @return list of trainings that ended after the specified time.
     */
    List<Training> findTrainingsEndedAfter(Date time);

    /**
     * Retrieves all trainings by activity type.
     *
     * @param activityType the type of activity.
     * @return list of trainings with the specified activity type.
     */
    List<Training> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Retrieves all trainings for a specific user that ended in the last month.
     *
     * @param userId the ID of the user.
     * @param startOfLastMonth the start date of the last month.
     * @param endOfLastMonth the end date of the last month.
     * @return list of trainings for the specified user that ended in the last month.
     */
    List<Training> findByUserIdFromLastMonth(Long userId, Date startOfLastMonth, Date endOfLastMonth);

}
