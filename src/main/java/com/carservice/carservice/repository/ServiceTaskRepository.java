package com.carservice.carservice.repository;

import com.carservice.carservice.domain.ServiceStatus;
import com.carservice.carservice.domain.ServiceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Long> {

    // For the scheduler: find tasks due within 7 days, not yet notified, not completed
    List<ServiceTask> findByDueDateBeforeAndNotificationSentFalseAndStatusNot(
            LocalDate date, ServiceStatus status);

    // For the UI notification banner: all non-completed tasks sorted by due date
    List<ServiceTask> findByStatusNotOrderByDueDateAsc(ServiceStatus status);
}
