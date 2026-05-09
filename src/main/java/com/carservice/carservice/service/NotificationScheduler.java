package com.carservice.carservice.service;

import com.carservice.carservice.domain.ServiceStatus;
import com.carservice.carservice.domain.ServiceTask;
import com.carservice.carservice.repository.ServiceTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * OOP CONCEPT: POLYMORPHISM IN ACTION
 * -------------------------------------
 * Spring injects a List of ALL classes implementing NotificationAction.
 * We loop through them and call send() — Java picks the right implementation
 * at runtime (ConsoleNotificationAction, EmailNotificationAction, SmsNotificationAction).
 * This is runtime polymorphism: one method call, three different behaviours.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final ServiceTaskRepository serviceTaskRepository;
    private final List<NotificationAction> notificationActions;

    @Scheduled(fixedRate = 60000)
    public void checkAndSendNotifications() {
        LocalDate noticeDate = LocalDate.now().plusDays(7);

        List<ServiceTask> dueTasks = serviceTaskRepository
                .findByDueDateBeforeAndNotificationSentFalseAndStatusNot(noticeDate, ServiceStatus.COMPLETED);

        for (ServiceTask task : dueTasks) {
            // Polymorphism: same send() call dispatches to 3 different implementations
            for (NotificationAction action : notificationActions) {
                action.send(task);
            }
            task.setNotificationSent(true);
            serviceTaskRepository.save(task);
        }

        if (!dueTasks.isEmpty()) {
            log.info("✅ Sent notifications for {} upcoming task(s).", dueTasks.size());
        }

        // Update status for tasks that have become overdue
        List<ServiceTask> activeTasks = serviceTaskRepository.findByStatusNotOrderByDueDateAsc(ServiceStatus.COMPLETED);
        int overdueCount = 0;
        for (ServiceTask task : activeTasks) {
            if (task.isOverdue() && task.getStatus() != ServiceStatus.OVERDUE) {
                task.setStatus(ServiceStatus.OVERDUE);
                serviceTaskRepository.save(task);
                overdueCount++;
            }
        }
        if (overdueCount > 0) {
            log.info("⚠️ Marked {} task(s) as OVERDUE.", overdueCount);
        }
    }
}
