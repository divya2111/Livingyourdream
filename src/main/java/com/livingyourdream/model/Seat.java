package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatid;

   // private Long vehicleId;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status = SeatStatus.AVAILABLE;

    private LocalDate travelDate;

    @ManyToOne
    @JoinColumn(name = "Transport_id")  // Matches DB column name
    @JsonIgnore
    private Transport transport;  // Matches `mappedBy` in Transport.java


}
