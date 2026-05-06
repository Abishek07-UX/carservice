package com.autotrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_records")
@Data
@NoArgsConstructor
public class RepairRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @NotBlank(message = "Problem description is required")
    @Column(columnDefinition = "TEXT")
    private String problemDescription;

    @NotBlank(message = "Status is required")
    private String status; // Pending, In Progress, Completed

    @Min(value = 0, message = "Repair cost must be 0 or more")
    private Double repairCost;

    private LocalDate reportedDate;
    private LocalDate resolvedDate;

    private String technicianName;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.reportedDate == null) this.reportedDate = LocalDate.now();
    }
}
