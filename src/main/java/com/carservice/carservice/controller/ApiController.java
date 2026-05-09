package com.carservice.carservice.controller;

import com.carservice.carservice.domain.Car;
import com.carservice.carservice.domain.ServiceTask;
import com.carservice.carservice.service.CarManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller — allows external systems to interact with Car data.
 * The WebController handles the Thymeleaf UI; this handles JSON responses.
 */
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class ApiController {

    private final CarManagementService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @PostMapping("/{carId}/tasks")
    public ResponseEntity<ServiceTask> addTask(@PathVariable Long carId, @RequestBody ServiceTask task) {
        ServiceTask saved = carService.saveServiceTask(task, carId);
        return saved != null ? ResponseEntity.ok(saved) : ResponseEntity.notFound().build();
    }
}
