package com.autotrack.repository;

import com.autotrack.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByCarId(String carId);
    Optional<Car> findByVehicleNumber(String vehicleNumber);
    List<Car> findByBrandIgnoreCase(String brand);
    List<Car> findByFuelTypeIgnoreCase(String fuelType);
    List<Car> findByOwnerNameContainingIgnoreCaseOrVehicleNumberContainingIgnoreCase(String ownerName, String vehicleNumber);

    @Query("SELECT DISTINCT c.brand FROM Car c ORDER BY c.brand")
    List<String> findDistinctBrands();

    @Query("SELECT COUNT(c) FROM Car c")
    long countAllCars();
}
