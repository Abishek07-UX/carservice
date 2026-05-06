package com.autotrack.repository;

import com.autotrack.domain.CarDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarDocumentRepository extends JpaRepository<CarDocument, Long> {
    List<CarDocument> findByCarIdOrderByUploadedAtDesc(Long carId);
    List<CarDocument> findByCarIdAndDocumentType(Long carId, String documentType);
}
