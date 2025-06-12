package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.livingyourdream.Enum.BookingStatus.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private LocalDate booking_date;

    private LocalDate StartDate;
    private LocalDate EndDate;

    private int NumOfPeople;

    private Double total_cost;
    @Enumerated(EnumType.STRING)
    private BookingStatus Status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Defaulttripplan defaultTripPlans;

    @OneToOne
    @JoinColumn(name = "customplan_id")
    private Customtripplan customTripPlans;

}
