package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class EmailServiceImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(EmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom("FitnessTrackerWSB@ftwsb.com");
            mailMessage.setTo(emailDto.toAddress());
            mailMessage.setSubject(emailDto.subject());
            mailMessage.setText(emailDto.content());

            mailSender.send(mailMessage);
            log.info("Email was send to {}", emailDto.toAddress());

        } catch (Exception e) {
            log.error("Failed to send email to {}: with message: {}", emailDto.toAddress(), e.getMessage());
        }
    }

}
