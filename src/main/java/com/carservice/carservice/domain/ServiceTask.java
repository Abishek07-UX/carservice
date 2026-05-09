package com.carservice.carservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

/**
 * OOP CONCEPT: INHERITANCE
 * -------------------------
 * ServiceTask extends BaseEntity to automatically inherit 'id' and 'createdAt'.
 * No need to redeclare those fields here.
 *
 * OOP CONCEPT: ENCAPSULATION
 * ---------------------------
 * All fields are private. Business rules (like completing a task or detecting overdue)
 * are encapsulated inside this class as methods — not scattered across controllers.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceTask extends BaseEntity {

    private String taskName;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status = ServiceStatus.PENDING;

    private boolean notificationSent;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    // -------------------------------------------------------------------------
    // OOP CONCEPT: ENCAPSULATION (Behavior Methods)
    // Business logic lives INSIDE the object. Other classes call these methods
    // instead of manually flipping boolean flags from the outside.
    // -------------------------------------------------------------------------

    /**
     * Marks this task as finished and records that notification was sent.
     * External classes cannot set 'status' or 'notificationSent' directly to achieve this —
     * they MUST call this method.
     */
    public void markAsFinished() {
        this.status = ServiceStatus.COMPLETED;
        this.notificationSent = true;
    }

    /**
     * Returns true if this task is past its due date and not yet completed.
     * The overdue logic is hidden here — callers just ask "isOverdue?" and get a yes/no.
     */
    public boolean isOverdue() {
        return dueDate != null
                && LocalDate.now().isAfter(dueDate)
                && status != ServiceStatus.COMPLETED;
    }

    /**
     * Convenience check used by the scheduler and UI.
     */
    public boolean isCompleted() {
        return status == ServiceStatus.COMPLETED;
    }
}
