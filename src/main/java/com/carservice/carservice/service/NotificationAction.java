package com.carservice.carservice.service;

import com.carservice.carservice.domain.ServiceTask;

/**
 * OOP CONCEPT: POLYMORPHISM (Interface / Contract)
 * -------------------------------------------------
 * This interface defines a CONTRACT: any class that implements it
 * guarantees it can send a notification via the send() method.
 *
 * We currently have THREE implementations:
 *   1. ConsoleNotificationAction  → prints to the server console
 *   2. EmailNotificationAction    → simulates sending an email
 *   3. SmsNotificationAction      → simulates sending an SMS
 *
 * The NotificationScheduler doesn't care WHICH one it's calling —
 * it calls send() on all of them the same way. That's Polymorphism!
 */
public interface NotificationAction {
    void send(ServiceTask task);
}
