package com.autotrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_records")
@Data
@NoArgsConstructor
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @NotNull(message = "Service date is required")
    private LocalDate serviceDate;

    @NotBlank(message = "Service type is required")
    private String serviceType; // Oil Change, Full Service, Tyre Rotation, etc.

    @Column(columnDefinition = "TEXT")
    private String description;

    @Min(value = 0, message = "Cost must be 0 or more")
    private Double cost;

    private String garageName;
    private String technicianName;

    private Double mileageAtService;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
