package com.autotrack.repository;

import com.autotrack.domain.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByCarIdOrderByServiceDateDesc(Long carId);

    @Query("SELECT SUM(s.cost) FROM ServiceRecord s WHERE s.car.id = :carId")
    Double getTotalCostByCarId(@Param("carId") Long carId);

    @Query("SELECT SUM(s.cost) FROM ServiceRecord s")
    Double getTotalServiceCost();

    @Query("SELECT MONTH(s.serviceDate), SUM(s.cost) FROM ServiceRecord s GROUP BY MONTH(s.serviceDate) ORDER BY MONTH(s.serviceDate)")
    List<Object[]> getMonthlyCosts();

    @Query("SELECT s.serviceType, COUNT(s) FROM ServiceRecord s GROUP BY s.serviceType")
    List<Object[]> getServiceTypeFrequency();
}
