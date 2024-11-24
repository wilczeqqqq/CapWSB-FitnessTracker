package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        log.info("Fetching Training with ID={}", trainingId);
        return trainingRepository.findById(trainingId);
    }

    /**
     * Gets all Users from the repository.
     *
     * @return List of all Users.
     */
    @Override
    public List<Training> findAllTrainings() {
        log.info("Fetching all Trainings");
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findTrainingsForUserWithId(Long userId) {
        log.info("Fetching all Trainings for User with ID={}", userId);
        return trainingRepository.findByUserId(userId);
    }

    public List<Training> findTrainingsEndedAfter(Date time) {
        log.info("Fetching all Trainings ended after {}", time);
        return trainingRepository.findByEndTimeAfter(time);
    }

//    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        log.info("Fetching all Trainings with activity type {}", activityType);
        return trainingRepository.findByActivityType(activityType);
    }
    @Override
    public List<Training> findByUserIdFromLastMonth(Long userId, Date startOfLastMonth, Date endOfLastMonth) {
        log.info("Fetching all Trainings from last month for userId {}", userId);
        return trainingRepository.findByUserIdFromLastMonth(userId, startOfLastMonth, endOfLastMonth);
    }

    public Training saveTraining(CreateOrUpdateTrainingDto createTrainingDto) {
        User user = userRepository.findById(createTrainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(createTrainingDto.getUserId()));
        Training training = newTrainingForUserWithTrainingDetails(user, createTrainingDto);
        log.info("Saving Training: {}", training);
        return trainingRepository.save(training);
    }

    private Training newTrainingForUserWithTrainingDetails(User user, CreateOrUpdateTrainingDto createTrainingDto) {
        return new Training(
                user,
                createTrainingDto.getStartTime(),
                createTrainingDto.getEndTime(),
                createTrainingDto.getActivityType(),
                createTrainingDto.getDistance(),
                createTrainingDto.getAverageSpeed()
        );
    }

}
