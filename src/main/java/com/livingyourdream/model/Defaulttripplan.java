package com.livingyourdream.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Defaulttripplan {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plan_id;
    private String title;
    private int duration_days;
    private double totalCost;
    private int peopleCount;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    @ManyToMany
    @JoinTable(
            name = "defaultplanplace",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private List<Place> places = new ArrayList<>();

   @ManyToMany
   @JoinTable(
           name = "defaultplantransport",
           joinColumns = @JoinColumn(name = "plan_id"),
           inverseJoinColumns = @JoinColumn(name = "transport_id")
   )
    private List<Transport> transports = new ArrayList<>();

   @ManyToMany
   @JoinTable(
           name = "defaultplanseat",
           joinColumns = @JoinColumn(name = "plan_id"),
           inverseJoinColumns = @JoinColumn(name = "seatid")
   )

   private List<Seat> seats = new ArrayList<>();

   @ManyToMany
    @JoinTable(
            name = "defaultplanstay",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "stay_id")
    )
    private List<Stay> stay = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "defaultplanstaybooking",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "sid")
    )
    private List<StayBooking> stays = new ArrayList<>();

}
