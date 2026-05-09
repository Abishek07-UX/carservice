package com.carservice.carservice.service;

import com.carservice.carservice.domain.ServiceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * OOP CONCEPT: POLYMORPHISM (Implementation 1 of 3)
 * --------------------------------------------------
 * Implements NotificationAction by printing an alert to the server console.
 */
@Component
@Slf4j
public class ConsoleNotificationAction implements NotificationAction {

    @Override
    public void send(ServiceTask task) {
        log.info("💻 CONSOLE ALERT: {} needs '{}' by {}",
                task.getCar().getDisplayName(), task.getTaskName(), task.getDueDate());
    }
}
