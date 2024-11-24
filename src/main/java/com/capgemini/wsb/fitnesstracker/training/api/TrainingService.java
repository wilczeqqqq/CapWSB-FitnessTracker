package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.CreateOrUpdateTrainingDto;

/**
 * Interface for managing training-related operations.
 */
public interface TrainingService {

    /**
     * Saves a new training.
     *
     * @param createTrainingDto the DTO containing the training details.
     * @return the saved Training.
     */
    Training saveTraining(CreateOrUpdateTrainingDto createTrainingDto);

    /**
     * Updates an existing training.
     *
     * @param trainingId the ID of the Training to be updated.
     * @param updateTrainingDto the DTO containing the updated training details.
     * @return the updated Training.
     */
    Training updateTraining(Long trainingId, CreateOrUpdateTrainingDto updateTrainingDto);

}
