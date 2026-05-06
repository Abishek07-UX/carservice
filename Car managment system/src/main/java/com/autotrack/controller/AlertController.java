package com.autotrack.controller;

import com.autotrack.domain.MaintenanceAlert;
import com.autotrack.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cars/{carId}/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/add")
    public String addAlertForm(@PathVariable Long carId, Model model) {
        model.addAttribute("carId", carId);
        model.addAttribute("alert", new MaintenanceAlert());
        return "alerts/add";
    }

    @PostMapping("/add")
    public String addAlert(@PathVariable Long carId,
                           @ModelAttribute MaintenanceAlert alert,
                           RedirectAttributes ra) {
        alertService.addAlert(carId, alert);
        ra.addFlashAttribute("successMsg", "Maintenance alert set!");
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{alertId}/dismiss")
    public String dismissAlert(@PathVariable Long carId,
                               @PathVariable Long alertId,
                               RedirectAttributes ra) {
        alertService.dismissAlert(alertId);
        ra.addFlashAttribute("successMsg", "Alert dismissed.");
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{alertId}/delete")
    public String deleteAlert(@PathVariable Long carId,
                              @PathVariable Long alertId,
                              RedirectAttributes ra) {
        alertService.deleteAlert(alertId);
        ra.addFlashAttribute("successMsg", "Alert deleted.");
        return "redirect:/cars/" + carId;
    }
}
