package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.Enum.StayStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class StayBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @ManyToOne
    @JoinColumn(name = "stay_id")
    @JsonIgnore
    private Stay stay;

    private int room_no;

    @Enumerated(EnumType.STRING)
    private StayStatus status = StayStatus.AVAILABLE;

   // private LocalDate Date;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int NumberOfGuests;
    private int NumberOfRooms;
    private Double totalPrice;


}
