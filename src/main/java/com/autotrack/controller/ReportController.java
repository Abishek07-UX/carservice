package com.autotrack.controller;

import com.autotrack.domain.Car;
import com.autotrack.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final CarService carService;
    private final ServiceRecordService serviceRecordService;
    private final RepairService repairService;

    @GetMapping
    public String reports(Model model) {
        List<Car> cars = carService.findAll();

        model.addAttribute("totalCars", carService.countAll());
        model.addAttribute("totalServiceCost", serviceRecordService.getOverallTotalCost());
        model.addAttribute("activeRepairs", repairService.countActiveRepairs());
        model.addAttribute("cars", cars);
        model.addAttribute("monthlyCosts", serviceRecordService.getMonthlyCosts());
        model.addAttribute("serviceFrequency", serviceRecordService.getServiceTypeFrequency());

        // Per-car cost breakdown
        cars.forEach(car -> {
            double svc = serviceRecordService.getTotalCostForCar(car.getId());
            double rep = repairService.getTotalRepairCostForCar(car.getId());
            car.setMileage(car.getMileage()); // just to reference the object
        });

        model.addAttribute("carServiceCosts", cars.stream().map(car ->
                new Object[]{car.getVehicleNumber() + " (" + car.getOwnerName() + ")",
                        serviceRecordService.getTotalCostForCar(car.getId()),
                        repairService.getTotalRepairCostForCar(car.getId())}
        ).toList());

        return "reports/index";
    }
}
