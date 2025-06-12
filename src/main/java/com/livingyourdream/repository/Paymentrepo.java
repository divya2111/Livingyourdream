package com.livingyourdream.repository;

import com.livingyourdream.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Paymentrepo extends JpaRepository<Payment,Long> {
    Payment findByBookingBookingId(Long bookingId);
}
