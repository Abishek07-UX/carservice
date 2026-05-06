package com.autotrack.service;

import com.autotrack.domain.Car;
import com.autotrack.domain.ServiceRecord;
import com.autotrack.repository.CarRepository;
import com.autotrack.repository.ServiceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final CarRepository carRepository;
    private final CarService carService;

    public ServiceRecord addServiceRecord(Long carId, ServiceRecord record) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
        record.setCar(car);
        ServiceRecord saved = serviceRecordRepository.save(record);
        carService.updateLastServiceDate(carId, record.getServiceDate());
        return saved;
    }

    public List<ServiceRecord> getServiceHistoryForCar(Long carId) {
        return serviceRecordRepository.findByCarIdOrderByServiceDateDesc(carId);
    }

    public Optional<ServiceRecord> findById(Long id) {
        return serviceRecordRepository.findById(id);
    }

    public void deleteServiceRecord(Long id) {
        serviceRecordRepository.deleteById(id);
    }

    public Double getTotalCostForCar(Long carId) {
        Double cost = serviceRecordRepository.getTotalCostByCarId(carId);
        return cost != null ? cost : 0.0;
    }

    public Double getOverallTotalCost() {
        Double cost = serviceRecordRepository.getTotalServiceCost();
        return cost != null ? cost : 0.0;
    }

    public List<Object[]> getMonthlyCosts() {
        return serviceRecordRepository.getMonthlyCosts();
    }

    public List<Object[]> getServiceTypeFrequency() {
        return serviceRecordRepository.getServiceTypeFrequency();
    }
}
