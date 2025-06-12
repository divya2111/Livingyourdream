package com.livingyourdream.model;

import com.livingyourdream.Enum.BookingStatus.BookingStatus;
import com.livingyourdream.Enum.PaymentMethod.paymentMethod;
import com.livingyourdream.Enum.PaymentStatus.paymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;
    private Double amount;
    private LocalDate paymentdate;

    @Enumerated(EnumType.STRING)
    private paymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private paymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;


}
