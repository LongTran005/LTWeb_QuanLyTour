package com.example.tourbookingsystem.controller;

import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.service.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/tours")
public class AdminTourController {

    private final TourService tourService;

    public AdminTourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public String listTours(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Tour> tours;

        if (keyword != null && !keyword.isEmpty()) {
            tours = tourService.searchTours(keyword);
        } else {
            tours = tourService.getAllTours();
        }

        model.addAttribute("tours", tours);
        model.addAttribute("keyword", keyword); // Để hiển thị lại từ khóa trên ô input sau khi tìm
        return "admin/tours";
    }

    @GetMapping("/new")
    public String newTour(Model model) {
        model.addAttribute("tour", new Tour());
        return "admin/tour_form";
    }

    @PostMapping("/save")
    public String saveTour(@ModelAttribute Tour tour) {
        tourService.save(tour);
        return "redirect:/admin/tours";
    }

    @GetMapping("/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.getTourById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        model.addAttribute("tour", tour);
        return "admin/tour_form";
    }

    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/admin/tours";
    }
}
