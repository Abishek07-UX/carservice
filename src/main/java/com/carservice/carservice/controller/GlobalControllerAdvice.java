package com.carservice.carservice.controller;

import com.carservice.carservice.service.CarManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CarManagementService carService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("upcomingTasks", carService.getUpcomingTasks());
    }
}
