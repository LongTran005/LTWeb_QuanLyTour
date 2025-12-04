package com.example.tourbookingsystem.controller;

import com.example.tourbookingsystem.entity.Booking;
import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.service.BookingService;
import com.example.tourbookingsystem.service.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;
    private final TourService tourService;

    public AdminBookingController(BookingService bookingService, TourService tourService) {
        this.bookingService = bookingService;
        this.tourService = tourService;
    }

    @GetMapping
    public String listBookings(@RequestParam(required = false) Long tourId,
                               Model model) {

        List<Booking> bookings;
        if (tourId != null) {
            bookings = bookingService.findByTourId(tourId);
            Tour tour = tourService.getTourById(tourId).orElse(null);
            model.addAttribute("selectedTour", tour);
        } else {
            bookings = bookingService.findAll();
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("tours", tourService.getAllTours());

        return "admin/bookings";
    }

    @PostMapping("/{id}/paid")
    public String markPaid(@PathVariable Long id) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setPaid(true);
        bookingService.save(booking);
        return "redirect:/admin/bookings";
    }
}
