package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * REST controller for Training management.
 * Provides endpoints for creating, getting, and updating trainings.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * Gets a list of all trainings.
     *
     * @return list of {@link TrainingDto} representing all trainings.
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    /**
     * Gets a list of trainings for a specific user by user ID.
     *
     * @param id the ID of the user.
     * @return list of {@link TrainingDto} representing the user's trainings.
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable("userId") Long id) {
        return trainingService.findTrainingsForUserWithId(id)
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    /**
     * Gets a list of trainings that ended after a specific date.
     *
     * @param date the date to compare against.
     * @return list of {@link TrainingDto} representing the trainings that ended after the specified date.
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getTrainingsEndedAfter(@PathVariable("afterTime") String date) {
        return trainingService.findTrainingsEndedAfter(Date.from(
                        LocalDate.parse(date).atStartOfDay(ZoneId.of("UTC")).toInstant())
                )
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    /**
     * Gets a list of trainings by activity type.
     *
     * @param activityType the type of activity.
     * @return list of {@link TrainingDto} representing the trainings with the specified activity type.
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingByActivity(@RequestParam("activityType") ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    /**
     * Creates a new training.
     *
     * @param createTrainingDto the DTO containing the training details.
     * @return the created {@link TrainingDto}.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody CreateOrUpdateTrainingDto createTrainingDto) {
        return trainingMapper.fromTraining(trainingService.saveTraining(createTrainingDto));
    }

    /**
     * Updates an existing training.
     *
     * @param id the ID of the training to be updated.
     * @param createTrainingDto the DTO containing the updated training details.
     * @return the updated {@link TrainingDto}.
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable("trainingId") Long id, @RequestBody CreateOrUpdateTrainingDto createTrainingDto) {
        return trainingMapper.fromTraining(trainingService.updateTraining(id, createTrainingDto));
    }
}
