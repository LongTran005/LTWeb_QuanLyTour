package com.example.tourbookingsystem.repository;

import com.example.tourbookingsystem.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
