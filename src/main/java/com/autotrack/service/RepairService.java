package com.autotrack.service;

import com.autotrack.domain.Car;
import com.autotrack.domain.RepairRecord;
import com.autotrack.repository.CarRepository;
import com.autotrack.repository.RepairRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RepairService {

    private final RepairRecordRepository repairRecordRepository;
    private final CarRepository carRepository;

    public RepairRecord addRepair(Long carId, RepairRecord record) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
        record.setCar(car);
        return repairRecordRepository.save(record);
    }

    public RepairRecord updateRepair(Long id, RepairRecord updated) {
        RepairRecord existing = repairRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found: " + id));
        existing.setProblemDescription(updated.getProblemDescription());
        existing.setStatus(updated.getStatus());
        existing.setRepairCost(updated.getRepairCost());
        existing.setTechnicianName(updated.getTechnicianName());
        existing.setNotes(updated.getNotes());
        existing.setResolvedDate(updated.getResolvedDate());
        return repairRecordRepository.save(existing);
    }

    public List<RepairRecord> getRepairsForCar(Long carId) {
        return repairRecordRepository.findByCarIdOrderByReportedDateDesc(carId);
    }

    public Optional<RepairRecord> findById(Long id) {
        return repairRecordRepository.findById(id);
    }

    public void deleteRepair(Long id) {
        repairRecordRepository.deleteById(id);
    }

    public Double getTotalRepairCostForCar(Long carId) {
        Double cost = repairRecordRepository.getTotalRepairCostByCarId(carId);
        return cost != null ? cost : 0.0;
    }

    public long countActiveRepairs() {
        return repairRecordRepository.countActiveRepairs();
    }
}
