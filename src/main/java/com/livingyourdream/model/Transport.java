package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.livingyourdream.Enum.Mode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Transport_id;
    @Enumerated(EnumType.STRING)
    private Mode mode;
    private String provider;
    private String vehicleNo;
    private String departurelocation;
    private String  arrivallocation;
    private LocalTime departure_time;
    private LocalTime arrival_time;
    private Double price;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
