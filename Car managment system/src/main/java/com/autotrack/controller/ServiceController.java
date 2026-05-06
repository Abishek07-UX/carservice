package com.autotrack.controller;

import com.autotrack.domain.ServiceRecord;
import com.autotrack.service.ServiceRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cars/{carId}/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceRecordService serviceRecordService;

    @GetMapping("/add")
    public String addServiceForm(@PathVariable Long carId, Model model) {
        model.addAttribute("carId", carId);
        model.addAttribute("serviceRecord", new ServiceRecord());
        return "services/add";
    }

    @PostMapping("/add")
    public String addService(@PathVariable Long carId,
                             @Valid @ModelAttribute ServiceRecord serviceRecord,
                             BindingResult result,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "services/add";
        }
        serviceRecordService.addServiceRecord(carId, serviceRecord);
        ra.addFlashAttribute("successMsg", "Service record added successfully!");
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{serviceId}/delete")
    public String deleteService(@PathVariable Long carId,
                                @PathVariable Long serviceId,
                                RedirectAttributes ra) {
        serviceRecordService.deleteServiceRecord(serviceId);
        ra.addFlashAttribute("successMsg", "Service record deleted.");
        return "redirect:/cars/" + carId;
    }
}
