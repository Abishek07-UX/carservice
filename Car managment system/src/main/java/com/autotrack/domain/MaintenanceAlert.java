package com.autotrack.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_alerts")
@Data
@NoArgsConstructor
public class MaintenanceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private String alertType;   // Oil Change, Full Service, Tyre Check, etc.
    private String message;

    private LocalDate dueDateAlert;
    private Double dueMileageAlert;

    private boolean dismissed = false;
    private boolean triggered = false;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
