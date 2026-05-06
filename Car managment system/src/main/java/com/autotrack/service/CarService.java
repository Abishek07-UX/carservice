package com.autotrack.service;

import com.autotrack.domain.Car;
import com.autotrack.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {

    private final CarRepository carRepository;

    public Car saveCar(Car car) {
        if (car.getCarId() == null || car.getCarId().isBlank()) {
            car.setCarId(generateCarId());
        }
        return carRepository.save(car);
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> findByCarId(String carId) {
        return carRepository.findByCarId(carId);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<Car> search(String query) {
        if (query == null || query.isBlank()) return carRepository.findAll();
        return carRepository.findByOwnerNameContainingIgnoreCaseOrVehicleNumberContainingIgnoreCase(query, query);
    }

    public List<Car> filterByBrand(String brand) {
        return carRepository.findByBrandIgnoreCase(brand);
    }

    public List<Car> filterByFuelType(String fuelType) {
        return carRepository.findByFuelTypeIgnoreCase(fuelType);
    }

    public List<String> getDistinctBrands() {
        return carRepository.findDistinctBrands();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public void updateLastServiceDate(Long carId, LocalDate date) {
        carRepository.findById(carId).ifPresent(car -> {
            car.setLastServiceDate(date);
            carRepository.save(car);
        });
    }

    public long countAll() {
        return carRepository.countAllCars();
    }

    public List<Car> sortByMileage() {
        List<Car> cars = carRepository.findAll();
        cars.sort(Comparator.comparingDouble(c -> c.getMileage() == null ? 0 : c.getMileage()));
        return cars;
    }

    public List<Car> sortByLastServiceDate() {
        List<Car> cars = carRepository.findAll();
        cars.sort(Comparator.comparing(c -> c.getLastServiceDate() == null ? LocalDate.MIN : c.getLastServiceDate()));
        return cars;
    }

    private String generateCarId() {
        String prefix = "CAR";
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(7);
        return prefix + timestamp;
    }
}
