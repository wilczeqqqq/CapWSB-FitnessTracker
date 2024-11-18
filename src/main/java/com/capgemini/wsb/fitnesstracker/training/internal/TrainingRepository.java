package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

interface TrainingRepository extends JpaRepository<Training, Long> {

    default List<Training> findByUserId(Long userId) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getUser().getId(), userId))
                .toList();
    }

    default List<Training> findByEndTimeAfter(Date time) {
        return findAll().stream()
                .filter(training -> training.getEndTime().after(time))
                .toList();
    }

    default List<Training> findByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getActivityType(), activityType))
                .toList();
    }
}
