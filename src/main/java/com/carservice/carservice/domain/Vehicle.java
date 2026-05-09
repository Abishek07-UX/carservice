package com.carservice.carservice.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP CONCEPT: INHERITANCE (Base Class) + ABSTRACTION
 * ----------------------------------------------------
 * 'Vehicle' is the BASE CLASS in our inheritance chain:
 *
 *   BaseEntity  <-- abstract, has id + createdAt
 *       |
 *   Vehicle     <-- abstract, has make + model; forces subclasses to implement getDisplayName()
 *       |
 *     Car        <-- concrete class, adds year + registrationNumber
 *
 * The abstract method 'getDisplayName()' cannot have a body here —
 * every subclass MUST provide its own implementation (Abstraction + Polymorphism).
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Vehicle extends BaseEntity {

    private String make;
    private String model;

    /**
     * OOP CONCEPT: ABSTRACTION (Abstract Method)
     * -------------------------------------------
     * No implementation here. Forces every Vehicle subclass (Car, Truck, etc.)
     * to define how it displays its own name.
     */
    public abstract String getDisplayName();
}
