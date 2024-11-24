package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.calendar.calendarUtils;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
public class EmailTrainingReport {

    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;
    private final EmailSender emailSender;

//    @Scheduled(cron = "0 * * * * *") // every minute *testing purposes*
    @Scheduled(cron = "0 0 0 1 * ?") // at 00:00 at 1 day of month.
    public void generateSummaryOfMonthlyTraining() {

        List<User> users = userProvider.findAllUsers();
        Date startOfLastMonth = calendarUtils.getStartOfLastMonth();
        Date endOfLastMonth = calendarUtils.getEndOfLastMonth();

        for (User user : users) {

            List<Training> trainingsForUser = trainingProvider.findByUserIdFromLastMonth(user.getId(), startOfLastMonth, endOfLastMonth);

            int trainingsCount = trainingsForUser.size();
            String subject = "Monthly Training Summary";
            String trainingWord = (trainingsCount == 1) ? "training" : "trainings";

            String content = "Congratulations " + user.getFirstName() + " " + user.getLastName() + " you have finished " + trainingsCount + " " + trainingWord + " last month!";

            EmailDto email = new EmailDto(user.getEmail(), subject, content);

            emailSender.send(email);

        }
    }
}
