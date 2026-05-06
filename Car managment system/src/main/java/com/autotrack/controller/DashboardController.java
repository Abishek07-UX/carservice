package com.autotrack.controller;

import com.autotrack.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final CarService carService;
    private final ServiceRecordService serviceRecordService;
    private final RepairService repairService;
    private final AlertService alertService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalCars", carService.countAll());
        model.addAttribute("totalServiceCost", serviceRecordService.getOverallTotalCost());
        model.addAttribute("activeRepairs", repairService.countActiveRepairs());
        model.addAttribute("activeAlerts", alertService.countAllActiveAlerts());
        model.addAttribute("recentCars", carService.findAll().stream().limit(5).toList());
        model.addAttribute("alerts", alertService.getActiveAlerts().stream().limit(5).toList());
        model.addAttribute("monthlyCosts", serviceRecordService.getMonthlyCosts());
        model.addAttribute("serviceFrequency", serviceRecordService.getServiceTypeFrequency());
        return "dashboard";
    }
}
