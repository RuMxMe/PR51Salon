package com.example.pr51salon.repository;

import com.example.pr51salon.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
   
}

