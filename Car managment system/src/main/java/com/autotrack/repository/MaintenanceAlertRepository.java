package com.autotrack.repository;

import com.autotrack.domain.MaintenanceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceAlertRepository extends JpaRepository<MaintenanceAlert, Long> {
    List<MaintenanceAlert> findByCarIdAndDismissedFalseOrderByDueDateAlertAsc(Long carId);

    @Query("SELECT a FROM MaintenanceAlert a WHERE a.dismissed = false AND " +
           "(a.dueDateAlert <= :soon OR a.dueMileageAlert <= :mileage) ORDER BY a.dueDateAlert ASC")
    List<MaintenanceAlert> findActiveAlerts(@Param("soon") LocalDate soon, @Param("mileage") Double mileage);

    @Query("SELECT COUNT(a) FROM MaintenanceAlert a WHERE a.dismissed = false AND a.car.id = :carId")
    long countActiveAlertsByCarId(@Param("carId") Long carId);

    @Query("SELECT COUNT(a) FROM MaintenanceAlert a WHERE a.dismissed = false")
    long countAllActiveAlerts();
}
