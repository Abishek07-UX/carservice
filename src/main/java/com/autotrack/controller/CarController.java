package com.autotrack.controller;

import com.autotrack.domain.Car;
import com.autotrack.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ServiceRecordService serviceRecordService;
    private final RepairService repairService;
    private final AlertService alertService;
    private final DocumentService documentService;

    @GetMapping
    public String listCars(Model model,
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) String brand,
                           @RequestParam(required = false) String fuelType,
                           @RequestParam(required = false) String sort) {
        List<Car> cars;
        if (search != null && !search.isBlank()) {
            cars = carService.search(search);
        } else if (brand != null && !brand.isBlank()) {
            cars = carService.filterByBrand(brand);
        } else if (fuelType != null && !fuelType.isBlank()) {
            cars = carService.filterByFuelType(fuelType);
        } else if ("mileage".equals(sort)) {
            cars = carService.sortByMileage();
        } else if ("date".equals(sort)) {
            cars = carService.sortByLastServiceDate();
        } else {
            cars = carService.findAll();
        }
        model.addAttribute("cars", cars);
        model.addAttribute("brands", carService.getDistinctBrands());
        model.addAttribute("search", search);
        model.addAttribute("brand", brand);
        model.addAttribute("fuelType", fuelType);
        model.addAttribute("sort", sort);
        return "cars/list";
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "cars/add";
    }

    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute Car car,
                         BindingResult result,
                         RedirectAttributes ra,
                         Model model) {
        if (result.hasErrors()) {
            return "cars/add";
        }
        carService.saveCar(car);
        ra.addFlashAttribute("successMsg", "Car registered successfully!");
        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public String viewCar(@PathVariable Long id, Model model) {
        Car car = carService.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        model.addAttribute("car", car);
        model.addAttribute("services", serviceRecordService.getServiceHistoryForCar(id));
        model.addAttribute("repairs", repairService.getRepairsForCar(id));
        model.addAttribute("alerts", alertService.getAlertsForCar(id));
        model.addAttribute("documents", documentService.getDocumentsForCar(id));
        model.addAttribute("totalServiceCost", serviceRecordService.getTotalCostForCar(id));
        model.addAttribute("totalRepairCost", repairService.getTotalRepairCostForCar(id));
        return "cars/detail";
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable Long id, Model model) {
        Car car = carService.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        model.addAttribute("car", car);
        return "cars/edit";
    }

    @PostMapping("/{id}/edit")
    public String editCar(@PathVariable Long id,
                          @Valid @ModelAttribute Car car,
                          BindingResult result,
                          RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "cars/edit";
        }
        car.setId(id);
        carService.saveCar(car);
        ra.addFlashAttribute("successMsg", "Car updated successfully!");
        return "redirect:/cars/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteCar(@PathVariable Long id, RedirectAttributes ra) {
        carService.deleteCar(id);
        ra.addFlashAttribute("successMsg", "Car deleted successfully!");
        return "redirect:/cars";
    }
}
