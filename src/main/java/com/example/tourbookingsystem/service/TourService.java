package com.example.tourbookingsystem.service;

import com.example.tourbookingsystem.entity.Tour;
import com.example.tourbookingsystem.repository.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    public List<Tour> searchTours(String keyword) {
        return tourRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Tour save(Tour tour) {
        // Logic kiểm tra đơn giản:
        if (tour.getCurrentPeople() > tour.getMaxPeople()) {
            throw new RuntimeException("Số người hiện tại không được lớn hơn số người tối đa!");
        }
        return tourRepository.save(tour);
    }

    public void deleteById(Long id) {
        tourRepository.deleteById(id);
    }
}
