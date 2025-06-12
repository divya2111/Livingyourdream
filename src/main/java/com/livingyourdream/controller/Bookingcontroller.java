package com.livingyourdream.controller;

import com.livingyourdream.dto.BookingRequestDTO;
import com.livingyourdream.model.Booking;
import com.livingyourdream.service.Bookingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class Bookingcontroller {

    @Autowired
    private Bookingservice bookingservice;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO dto, Authentication authencation ) {
        Booking saved = bookingservice.createBooking(dto,authencation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingservice.getAllBookings();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingservice.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingservice.cancelBooking(bookingId));
    }
}
