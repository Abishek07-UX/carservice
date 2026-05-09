package com.carservice.carservice.controller;

import com.carservice.carservice.domain.Car;
import com.carservice.carservice.domain.ServiceTask;
import com.carservice.carservice.service.CarManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final CarManagementService carService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "index";
    }

    @GetMapping("/car/new")
    public String newCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "car-form";
    }

    @PostMapping("/car")
    public String saveCar(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "redirect:/";
    }

    @GetMapping("/car/{id}")
    public String viewCar(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        if (car == null) return "redirect:/";
        model.addAttribute("car", car);
        model.addAttribute("newTask", new ServiceTask());
        return "car-details";
    }

    @PostMapping("/car/{id}/task")
    public String saveTask(@PathVariable Long id, @ModelAttribute ServiceTask task) {
        carService.saveServiceTask(task, id);
        return "redirect:/car/" + id;
    }

    @PostMapping("/task/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId, @RequestParam Long carId) {
        carService.markTaskAsCompleted(taskId);
        return "redirect:/car/" + carId;
    }

    @PostMapping("/task/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId, @RequestParam Long carId) {
        carService.deleteTask(taskId);
        return "redirect:/car/" + carId;
    }

    @PostMapping("/car/{id}/delete")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/";
    }
}