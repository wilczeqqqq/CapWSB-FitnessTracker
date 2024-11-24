package com.capgemini.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@EnableScheduling
class MailConfig {
}
