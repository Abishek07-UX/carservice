package com.autotrack.service;

import com.autotrack.domain.Car;
import com.autotrack.domain.CarDocument;
import com.autotrack.repository.CarDocumentRepository;
import com.autotrack.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final CarDocumentRepository documentRepository;
    private final CarRepository carRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    public CarDocument uploadDocument(Long carId, MultipartFile file, String docType) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetPath = uploadPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        CarDocument doc = new CarDocument();
        doc.setCar(car);
        doc.setDocumentType(docType);
        doc.setFileName(uniqueName);
        doc.setOriginalFileName(file.getOriginalFilename());
        doc.setFilePath(targetPath.toString());
        doc.setContentType(file.getContentType());
        doc.setFileSize(file.getSize());

        return documentRepository.save(doc);
    }

    public List<CarDocument> getDocumentsForCar(Long carId) {
        return documentRepository.findByCarIdOrderByUploadedAtDesc(carId);
    }

    public Optional<CarDocument> findById(Long id) {
        return documentRepository.findById(id);
    }

    public Resource loadAsResource(CarDocument doc) throws MalformedURLException {
        Path filePath = Paths.get(doc.getFilePath()).normalize();
        return new UrlResource(filePath.toUri());
    }

    public void deleteDocument(Long id) throws IOException {
        documentRepository.findById(id).ifPresent(doc -> {
            try {
                Files.deleteIfExists(Paths.get(doc.getFilePath()));
            } catch (IOException e) {
                // log and continue
            }
            documentRepository.delete(doc);
        });
    }
}
