package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Repository interface for managing Training entities.
 */
interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Finds all trainings for a specific user by user ID.
     *
     * @param userId the ID of the user.
     * @return list of trainings for the specified user.
     */
    default List<Training> findByUserId(Long userId) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getUser().getId(), userId))
                .toList();
    }

    /**
     * Finds all trainings that ended after a specific time.
     *
     * @param time the time to compare against.
     * @return list of trainings that ended after the specified time.
     */
    default List<Training> findByEndTimeAfter(Date time) {
        return findAll().stream()
                .filter(training -> training.getEndTime().after(time))
                .toList();
    }

    /**
     * Finds all trainings by activity type.
     *
     * @param activityType the type of activity.
     * @return list of trainings with the specified activity type.
     */
    default List<Training> findByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getActivityType(), activityType))
                .toList();
    }

    /**
     * Finds all trainings for a specific user that ended in the last month.
     *
     * @param userId the ID of the user.
     * @param startOfLastMonth the start date of the last month.
     * @param endOfLastMonth the end date of the last month.
     * @return list of trainings for the specified user that ended in the last month.
     */
    default List<Training> findByUserIdFromLastMonth(Long userId, Date startOfLastMonth, Date endOfLastMonth) {
        return findAll().stream()
                .filter(training ->
                        Objects.equals(training.getUser().getId(), userId) &&
                                !training.getEndTime().before(startOfLastMonth) &&
                                !training.getEndTime().after(endOfLastMonth)
                )
                .toList();
    }
}
