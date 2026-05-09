package com.carservice.carservice.service;

import com.carservice.carservice.domain.ServiceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * OOP CONCEPT: POLYMORPHISM (Implementation 2 of 3)
 * --------------------------------------------------
 * Implements NotificationAction by simulating an email to the car owner.
 */
@Component
@Slf4j
public class EmailNotificationAction implements NotificationAction {

    @Override
    public void send(ServiceTask task) {
        log.info("📧 EMAIL ALERT: Your {} is due for '{}' — please schedule a service.",
                task.getCar().getDisplayName(), task.getTaskName());
    }
}
