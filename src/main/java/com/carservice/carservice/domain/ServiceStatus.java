package com.carservice.carservice.domain;

/**
 * OOP CONCEPT: ABSTRACTION (Enum)
 * --------------------------------
 * Represents the possible states of a ServiceTask.
 * The UI and business logic use this enum instead of raw boolean flags,
 * making the code more readable and less error-prone.
 */
public enum ServiceStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    OVERDUE
}
