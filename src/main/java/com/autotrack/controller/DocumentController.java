package com.autotrack.controller;

import com.autotrack.domain.CarDocument;
import com.autotrack.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

@Controller
@RequestMapping("/cars/{carId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public String listDocuments(@PathVariable Long carId, Model model) {
        model.addAttribute("carId", carId);
        model.addAttribute("documents", documentService.getDocumentsForCar(carId));
        return "documents/list";
    }

    @PostMapping("/upload")
    public String uploadDocument(@PathVariable Long carId,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("documentType") String documentType,
                                 RedirectAttributes ra) {
        if (file.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Please select a file to upload.");
            return "redirect:/cars/" + carId;
        }
        try {
            documentService.uploadDocument(carId, file, documentType);
            ra.addFlashAttribute("successMsg", "Document uploaded successfully!");
        } catch (IOException e) {
            ra.addFlashAttribute("errorMsg", "Upload failed: " + e.getMessage());
        }
        return "redirect:/cars/" + carId;
    }

    @GetMapping("/{docId}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long carId,
                                                     @PathVariable Long docId) {
        CarDocument doc = documentService.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        try {
            Resource resource = documentService.loadAsResource(doc);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + doc.getOriginalFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Could not download file", e);
        }
    }

    @PostMapping("/{docId}/delete")
    public String deleteDocument(@PathVariable Long carId,
                                 @PathVariable Long docId,
                                 RedirectAttributes ra) {
        try {
            documentService.deleteDocument(docId);
            ra.addFlashAttribute("successMsg", "Document deleted.");
        } catch (IOException e) {
            ra.addFlashAttribute("errorMsg", "Delete failed: " + e.getMessage());
        }
        return "redirect:/cars/" + carId;
    }
}
