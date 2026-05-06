package com.autotrack.service;

import com.autotrack.domain.Car;
import com.autotrack.domain.MaintenanceAlert;
import com.autotrack.repository.CarRepository;
import com.autotrack.repository.MaintenanceAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {

    private final MaintenanceAlertRepository alertRepository;
    private final CarRepository carRepository;

    public MaintenanceAlert addAlert(Long carId, MaintenanceAlert alert) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
        alert.setCar(car);
        return alertRepository.save(alert);
    }

    public List<MaintenanceAlert> getAlertsForCar(Long carId) {
        return alertRepository.findByCarIdAndDismissedFalseOrderByDueDateAlertAsc(carId);
    }

    public List<MaintenanceAlert> getActiveAlerts() {
        LocalDate soon = LocalDate.now().plusDays(30);
        return alertRepository.findActiveAlerts(soon, Double.MAX_VALUE);
    }

    public void dismissAlert(Long alertId) {
        alertRepository.findById(alertId).ifPresent(a -> {
            a.setDismissed(true);
            alertRepository.save(a);
        });
    }

    public void deleteAlert(Long alertId) {
        alertRepository.deleteById(alertId);
    }

    public long countAllActiveAlerts() {
        return alertRepository.countAllActiveAlerts();
    }
}
