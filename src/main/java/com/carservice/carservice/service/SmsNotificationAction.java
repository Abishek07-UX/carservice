package com.carservice.carservice.service;

import com.carservice.carservice.domain.ServiceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * OOP CONCEPT: POLYMORPHISM (Implementation 3 of 3)
 * --------------------------------------------------
 * Implements NotificationAction by simulating an SMS to the car owner.
 * Spring injects ALL three implementations automatically — the scheduler
 * calls send() on each one without knowing which type it is.
 */
@Component
@Slf4j
public class SmsNotificationAction implements NotificationAction {

    @Override
    public void send(ServiceTask task) {
        log.info("📱 SMS ALERT: [AutoCare] Reminder — your {} needs '{}' by {}. Book now!",
                task.getCar().getDisplayName(), task.getTaskName(), task.getDueDate());
    }
}
