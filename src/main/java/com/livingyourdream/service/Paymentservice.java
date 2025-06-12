package com.livingyourdream.service;

import com.livingyourdream.Enum.BookingStatus.BookingStatus;
import com.livingyourdream.Enum.PaymentMethod.paymentMethod;
import com.livingyourdream.Enum.PaymentStatus.paymentStatus;
import com.livingyourdream.model.Booking;
import com.livingyourdream.model.Payment;
import com.livingyourdream.repository.Bookingrepo;
import com.livingyourdream.repository.Paymentrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class Paymentservice {


    @Autowired
    private  Bookingrepo bookingrepo;
    @Autowired
    private  Paymentrepo paymentrepo;



    public Payment createPayment(Long bookingId ,Payment paymentRequest) {
        Booking booking = bookingrepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check if payment already exists for this booking
        if (paymentrepo.findByBookingBookingId(bookingId) != null) {
            throw new IllegalStateException("Payment already exists for this booking.");
        }

        // Check if booking is in the correct state
//        if (!"PENDING_PAYMENT".equalsIgnoreCase(booking.getStatus())) {
//            throw new IllegalStateException("Booking is not in a payable state.");
//        }

        Payment payments = new Payment();
        payments.setBooking(booking);
        payments.setPaymentdate(LocalDate.now());
        payments.setAmount(booking.getTotal_cost());
        payments.setPaymentMethod(paymentRequest.getPaymentMethod());
        payments.setPaymentStatus(paymentStatus.SUCCESS);
        payments.setBookingStatus(BookingStatus.CONFIRMED);

        Payment savedPayment = paymentrepo.save(payments);

        // Update booking with payment and change status
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingrepo.save(booking);

        return savedPayment;
    }


    public Payment getpaymentById(Long id){
        return paymentrepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Payment not Found"));
    }
    public Payment getPaymentByBookingId(Long bookingId){
        return paymentrepo.findByBookingBookingId(bookingId);
    }
}
