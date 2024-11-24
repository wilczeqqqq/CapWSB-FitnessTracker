package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * Service implementation for sending emails.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class EmailServiceImpl implements EmailSender {

    private final JavaMailSender mailSender;

    /**
     * Sends an email asynchronously.
     *
     * @param emailDto the DTO containing the email details.
     */
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
            log.info("Email was sent to {}", emailDto.toAddress());

        } catch (Exception e) {
            log.error("Failed to send email to {}: with message: {}", emailDto.toAddress(), e.getMessage());
        }
    }

}
