package com.autotrack.controller;

import com.autotrack.domain.RepairRecord;
import com.autotrack.service.RepairService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cars/{carId}/repairs")
@RequiredArgsConstructor
public class RepairController {

    private final RepairService repairService;

    @GetMapping("/add")
    public String addRepairForm(@PathVariable Long carId, Model model) {
        model.addAttribute("carId", carId);
        model.addAttribute("repairRecord", new RepairRecord());
        return "repairs/add";
    }

    @PostMapping("/add")
    public String addRepair(@PathVariable Long carId,
                            @Valid @ModelAttribute RepairRecord repairRecord,
                            BindingResult result,
                            RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "repairs/add";
        }
        repairService.addRepair(carId, repairRecord);
        ra.addFlashAttribute("successMsg", "Repair issue logged successfully!");
        return "redirect:/cars/" + carId;
    }

    @GetMapping("/{repairId}/edit")
    public String editRepairForm(@PathVariable Long carId,
                                 @PathVariable Long repairId, Model model) {
        model.addAttribute("carId", carId);
        model.addAttribute("repairRecord", repairService.findById(repairId)
                .orElseThrow(() -> new RuntimeException("Repair not found")));
        return "repairs/edit";
    }

    @PostMapping("/{repairId}/edit")
    public String editRepair(@PathVariable Long carId,
                             @PathVariable Long repairId,
                             @Valid @ModelAttribute RepairRecord repairRecord,
                             BindingResult result,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "repairs/edit";
        }
        repairService.updateRepair(repairId, repairRecord);
        ra.addFlashAttribute("successMsg", "Repair updated successfully!");
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{repairId}/delete")
    public String deleteRepair(@PathVariable Long carId,
                               @PathVariable Long repairId,
                               RedirectAttributes ra) {
        repairService.deleteRepair(repairId);
        ra.addFlashAttribute("successMsg", "Repair record deleted.");
        return "redirect:/cars/" + carId;
    }
}
