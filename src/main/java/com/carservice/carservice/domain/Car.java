package com.carservice.carservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * OOP CONCEPT: INHERITANCE (Subclass)
 * ------------------------------------
 * 'Car' extends 'Vehicle', which extends 'BaseEntity'.
 * Car automatically inherits: id, createdAt (from BaseEntity), make, model (from Vehicle).
 * It only needs to define what makes a Car unique: year and registrationNumber.
 *
 * OOP CONCEPT: ENCAPSULATION
 * ---------------------------
 * All fields are private. External classes access them only through the
 * generated getter/setter methods — never directly.
 *
 * OOP CONCEPT: POLYMORPHISM (Method Override)
 * --------------------------------------------
 * Car provides its OWN version of getDisplayName() from Vehicle.
 * This is runtime polymorphism — the method call resolves to THIS implementation.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car extends Vehicle {

    @Column(name = "manufacture_year")
    private int year;

    private String registrationNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceTask> serviceTasks = new ArrayList<>();

    /**
     * OOP CONCEPT: POLYMORPHISM (Overriding abstract method from Vehicle)
     * Returns a human-readable name for this specific car.
     */
    @Override
    public String getDisplayName() {
        return year + " " + getMake() + " " + getModel();
    }
}
