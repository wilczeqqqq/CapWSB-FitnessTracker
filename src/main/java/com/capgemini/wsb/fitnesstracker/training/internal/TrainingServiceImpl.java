package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
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

/**
 * Service implementation for managing Trainings.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    /**
     * Fetches a Training by its ID.
     *
     * @param trainingId the ID of the Training.
     * @return an Optional containing the Training if found, or empty if not found.
     */
    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        log.info("Fetching Training with ID={}", trainingId);
        return trainingRepository.findById(trainingId);
    }

    /**
     * Gets all Trainings from the repository.
     *
     * @return list of all Trainings.
     */
    @Override
    public List<Training> findAllTrainings() {
        log.info("Fetching all Trainings");
        return trainingRepository.findAll();
    }

    /**
     * Gets all Trainings for a specific user by user ID.
     *
     * @param userId the ID of the user.
     * @return list of Trainings for the specified user.
     */
    @Override
    public List<Training> findTrainingsForUserWithId(Long userId) {
        log.info("Fetching all Trainings for User with ID={}", userId);
        return trainingRepository.findByUserId(userId);
    }

    /**
     * Gets all Trainings that ended after a specific time.
     *
     * @param time the time to compare against.
     * @return list of Trainings that ended after the specified time.
     */
    @Override
    public List<Training> findTrainingsEndedAfter(Date time) {
        log.info("Fetching all Trainings ended after {}", time);
        return trainingRepository.findByEndTimeAfter(time);
    }

    /**
     * Gets all Trainings by activity type.
     *
     * @param activityType the type of activity.
     * @return list of Trainings with the specified activity type.
     */
    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        log.info("Fetching all Trainings with activity type {}", activityType);
        return trainingRepository.findByActivityType(activityType);
    }

    /**
     * Gets all Trainings for a specific user that ended in the last month.
     *
     * @param userId the ID of the user.
     * @param startOfLastMonth the start date of the last month.
     * @param endOfLastMonth the end date of the last month.
     * @return list of Trainings for the specified user that ended in the last month.
     */
    @Override
    public List<Training> findByUserIdFromLastMonth(Long userId, Date startOfLastMonth, Date endOfLastMonth) {
        log.info("Fetching all Trainings from last month for userId {}", userId);
        return trainingRepository.findByUserIdFromLastMonth(userId, startOfLastMonth, endOfLastMonth);
    }

    /**
     * Saves a new Training.
     *
     * @param createTrainingDto the DTO containing the training details.
     * @return the saved Training.
     */
    @Override
    public Training saveTraining(CreateOrUpdateTrainingDto createTrainingDto) {
        User user = userRepository.findById(createTrainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(createTrainingDto.getUserId()));
        Training training = newTrainingForUserWithTrainingDetails(user, createTrainingDto);
        log.info("Saving Training: {}", training);
        return trainingRepository.save(training);
    }

    /**
     * Updates an existing Training.
     *
     * @param trainingId the ID of the Training to be updated.
     * @param updateTrainingDto the DTO containing the updated training details.
     * @return the updated Training.
     */
    @Override
    public Training updateTraining(Long trainingId, CreateOrUpdateTrainingDto updateTrainingDto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
        if (updateTrainingDto.getUserId() != null) {
            User user = userRepository.findById(updateTrainingDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(updateTrainingDto.getUserId()));
            training.setUser(user);
        }
        training.setStartTime(updateTrainingDto.getStartTime());
        training.setEndTime(updateTrainingDto.getEndTime());
        training.setActivityType(updateTrainingDto.getActivityType());
        training.setDistance(updateTrainingDto.getDistance());
        training.setAverageSpeed(updateTrainingDto.getAverageSpeed());
        log.info("Updating Training: {}", training);
        return trainingRepository.save(training);
    }

    /**
     * Creates a new Training for a user with the provided training details.
     *
     * @param user the user for whom the training is created.
     * @param createTrainingDto the DTO containing the training details.
     * @return the created Training.
     */
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
