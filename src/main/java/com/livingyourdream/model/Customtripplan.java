package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Customtripplan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customplan_id;
    private String title;
    private int duration_days;
    private LocalDate create_date;
    private Double totalCost;
    private int peopleCount;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToMany
    @JoinTable(
            name = "customplan",
            joinColumns = @JoinColumn(name = "customplan_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private List<Place> places = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "customplantransport",
            joinColumns = @JoinColumn(name = "customplan_id"),
            inverseJoinColumns = @JoinColumn(name = "Transport_id")
    )
    private List<Transport> transports = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "customplanseat",
            joinColumns = @JoinColumn(name = "customplan_id"),
            inverseJoinColumns = @JoinColumn(name = "seatid")
    )

    private List<Seat> seats = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "customplanstay",
            joinColumns = @JoinColumn(name = "customplan_id"),
            inverseJoinColumns = @JoinColumn(name = "stay_id")
    )
    private List<Stay> stay = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "customplanstaybooking",
            joinColumns = @JoinColumn(name = "customplan_id"),
            inverseJoinColumns = @JoinColumn(name = "sid")
    )
    private List<StayBooking> stays= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
