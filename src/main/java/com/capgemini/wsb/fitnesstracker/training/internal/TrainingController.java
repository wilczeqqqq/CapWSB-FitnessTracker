package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
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
 * REST controller for User management.
 * Provides endpoints for creating, getting, updating, and deleting users.
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
     * @return list of {@link Training}  representing all users.
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

//    @Deprecated
//    @GetMapping("/{trainingId}")
//    public TrainingDto getTrainingById(@PathVariable("trainingId") Long id) {
//        return (trainingService.getTraining(id)
//                .map(trainingMapper::fromTraining)
//                .orElseThrow(() -> new TrainingNotFoundException(id)));
//    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable("userId") Long id) {
        return trainingService.findTrainingsForUserWithId(id)
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getTrainingsEndedAfter(@PathVariable("afterTime") String date) {
        return trainingService.findTrainingsEndedAfter(Date.from(
                LocalDate.parse(date).atStartOfDay(ZoneId.of("UTC")).toInstant())
                )
                    .stream()
                    .map(trainingMapper::fromTraining)
                    .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingByActivity(@RequestParam("activityType") ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::fromTraining)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody CreateOrUpdateTrainingDto createTrainingDto) {
        return trainingMapper.fromTraining(trainingService.saveTraining(createTrainingDto));
    }

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable("trainingId") Long id, @RequestBody CreateOrUpdateTrainingDto createTrainingDto) {
        return trainingMapper.fromTraining(trainingService.updateTraining(id, createTrainingDto));
    }
}
