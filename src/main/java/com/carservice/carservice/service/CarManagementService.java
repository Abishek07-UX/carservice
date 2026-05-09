package com.carservice.carservice.service;

import com.carservice.carservice.domain.Car;
import com.carservice.carservice.domain.ServiceStatus;
import com.carservice.carservice.domain.ServiceTask;
import com.carservice.carservice.repository.CarRepository;
import com.carservice.carservice.repository.ServiceTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OOP CONCEPT: ABSTRACTION
 * -------------------------
 * This service class ABSTRACTS away all database and business logic.
 * The Controller just calls saveCar(), getUpcomingTasks(), etc.
 * It has no idea about SQL, JPA, or repositories — that complexity is hidden here.
 */
@Service
@RequiredArgsConstructor
public class CarManagementService {

    private final CarRepository carRepository;
    private final ServiceTaskRepository serviceTaskRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<ServiceTask> getUpcomingTasks() {
        return serviceTaskRepository.findByStatusNotOrderByDueDateAsc(ServiceStatus.COMPLETED);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public ServiceTask saveServiceTask(ServiceTask task, Long carId) {
        Car car = getCarById(carId);
        if (car == null) return null;
        task.setCar(car);
        return serviceTaskRepository.save(task);
    }

    public void markTaskAsCompleted(Long taskId) {
        serviceTaskRepository.findById(taskId).ifPresent(task -> {
            // Uses the encapsulated method — not direct field manipulation
            task.markAsFinished();
            serviceTaskRepository.save(task);
        });
    }

    public void deleteTask(Long taskId) {
        serviceTaskRepository.deleteById(taskId);
    }
}
