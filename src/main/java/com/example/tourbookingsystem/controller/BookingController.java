package com.example.tourbookingsystem.controller;

import com.example.tourbookingsystem.entity.Booking;
import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.service.BookingService;
import com.example.tourbookingsystem.service.TourService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final TourService tourService;
    private final BookingService bookingService;

    public BookingController(TourService tourService, BookingService bookingService) {
        this.tourService = tourService;
        this.bookingService = bookingService;
    }

    // Hiển thị form đặt tour
    @GetMapping("/tour/{tourId}")
    public String showBookingForm(@PathVariable Long tourId, Model model) {
        Tour tour = tourService.getTourById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        Booking booking = new Booking();
        booking.setTour(tour);

        model.addAttribute("tour", tour);
        model.addAttribute("booking", booking);
        return "user/booking_form";
    }

    // Xử lý submit form
    @PostMapping("/tour/{tourId}")
    public String submitBooking(@PathVariable Long tourId,
                                @ModelAttribute("booking") Booking booking,
                                Model model) {

        Tour tour = tourService.getTourById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        booking.setTour(tour);
        double totalPrice = tour.getPrice() * booking.getNumberOfPeople();
        booking.setTotalPrice(totalPrice);
        booking.setPaid(false);

        bookingService.save(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("tour", tour);

        // chỗ này sau bạn có thể thêm gửi email xác nhận
        return "user/booking_success";
    }
}
