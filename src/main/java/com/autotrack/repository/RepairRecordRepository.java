package com.autotrack.repository;

import com.autotrack.domain.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {
    List<RepairRecord> findByCarIdOrderByReportedDateDesc(Long carId);
    List<RepairRecord> findByStatus(String status);

    @Query("SELECT SUM(r.repairCost) FROM RepairRecord r WHERE r.car.id = :carId")
    Double getTotalRepairCostByCarId(@Param("carId") Long carId);

    @Query("SELECT COUNT(r) FROM RepairRecord r WHERE r.status = 'Pending' OR r.status = 'In Progress'")
    long countActiveRepairs();
}
