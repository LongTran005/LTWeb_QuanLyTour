package com.example.tourbookingsystem.service;

import com.example.tourbookingsystem.entity.Booking;
import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByTour(Tour tour) {
        return bookingRepository.findByTour(tour);
    }

    public List<Booking> findByTourId(Long tourId) {
        return bookingRepository.findByTourId(tourId);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }
}
