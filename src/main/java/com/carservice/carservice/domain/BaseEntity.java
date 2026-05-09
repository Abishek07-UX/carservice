package com.carservice.carservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * OOP CONCEPT: ABSTRACTION
 * -------------------------
 * An abstract class cannot be instantiated directly.
 * It provides a common template for all entity classes (Car, ServiceTask).
 * Subclasses inherit the 'id' and 'createdAt' fields automatically —
 * they don't need to know HOW these are generated, just THAT they exist.
 */
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
