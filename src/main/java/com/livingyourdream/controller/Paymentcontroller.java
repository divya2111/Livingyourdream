package com.livingyourdream.controller;

import com.livingyourdream.model.Payment;
import com.livingyourdream.repository.Bookingrepo;
import com.livingyourdream.repository.Paymentrepo;
import com.livingyourdream.service.Paymentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class Paymentcontroller {

    @Autowired
    private Paymentservice paymentservice;

    @Autowired
    private Bookingrepo bookingrepo;

    @Autowired
    private Paymentrepo paymentrepo;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam Long bookingId, @RequestBody Payment paymentRequest) {
        Payment savedPayment = paymentservice.createPayment(bookingId, paymentRequest);
        return ResponseEntity.ok(savedPayment);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id){
        return paymentservice.getpaymentById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/booking/{bookingId})")
    public Payment getPaymentByBooking(@PathVariable Long bookingId){
        return paymentservice.getPaymentByBookingId(bookingId);
    }
}
