package com.autotrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String carId;

    @NotBlank(message = "Owner name is required")
    @Column(nullable = false)
    private String ownerName;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9+\\-\\s]{7,15}$", message = "Invalid contact number")
    private String contactNumber;

    @NotBlank(message = "Vehicle number is required")
    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2100, message = "Year is invalid")
    private Integer yearOfManufacture;

    @NotBlank(message = "Fuel type is required")
    @Column(nullable = false)
    private String fuelType;   // Petrol, Diesel, Electric, Hybrid

    @NotBlank(message = "Transmission is required")
    private String transmission;  // Manual, Automatic

    private String color;

    @Min(value = 0, message = "Mileage must be 0 or more")
    private Double mileage;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDate lastServiceDate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceRecord> serviceRecords;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepairRecord> repairRecords;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaintenanceAlert> maintenanceAlerts;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarDocument> documents;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
