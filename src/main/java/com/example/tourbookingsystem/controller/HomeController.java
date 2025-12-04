package com.example.tourbookingsystem.controller;

import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.service.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final TourService tourService;

    public HomeController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String location,
                       @RequestParam(required = false) Integer days,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice,
                       Model model) {

        List<Tour> tours = tourService.getAllTours();

        // Filter đơn giản bằng Java (cho dễ làm bài)
        tours = tours.stream().filter(tour -> {
            boolean match = true;
            if (location != null && !location.isEmpty()) {
                match = match && tour.getLocation() != null &&
                        tour.getLocation().toLowerCase().contains(location.toLowerCase());
            }
            if (days != null) {
                match = match && tour.getDays() == days;
            }
            if (minPrice != null) {
                match = match && tour.getPrice() >= minPrice;
            }
            if (maxPrice != null) {
                match = match && tour.getPrice() <= maxPrice;
            }
            return match;
        }).toList();

        model.addAttribute("tours", tours);
        return "user/home";
    }

    @GetMapping("/tour/{id}")
    public String tourDetail(@PathVariable Long id, Model model) {
        Tour tour = tourService.getTourById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        model.addAttribute("tour", tour);
        return "user/tour_detail";
    }
}
