package com.capgemini.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@EnableScheduling
class MailConfig {
}
