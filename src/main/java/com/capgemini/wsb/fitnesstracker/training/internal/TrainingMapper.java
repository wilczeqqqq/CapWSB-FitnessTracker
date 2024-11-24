package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Training and TrainingDto objects.
 */
@Component
@RequiredArgsConstructor
class TrainingMapper {

    private final UserMapper userMapper;

    /**
     * Converts a Training object to a TrainingDto object.
     *
     * @param training the Training object to convert.
     * @return the converted TrainingDto object.
     */
    TrainingDto fromTraining(Training training) {
        return TrainingDto.builder()
                .Id(training.getId())
                .user(userMapper.toDto(training.getUser()))
                .startTime(training.getStartTime())
                .endTime(training.getEndTime())
                .distance(training.getDistance())
                .averageSpeed(training.getAverageSpeed())
                .activityType(training.getActivityType())
                .build();
    }
}
