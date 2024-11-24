package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.CreateOrUpdateTrainingDto;

public interface TrainingService {

    Training saveTraining(CreateOrUpdateTrainingDto createTrainingDto);

    Training updateTraining(Long trainingId, CreateOrUpdateTrainingDto updateTrainingDto);

}
