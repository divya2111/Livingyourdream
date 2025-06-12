package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stay_id;
    private String name;
    private String city;
    private String state;
    private String country;
    private String type;
    private Double price;
    private String contact;

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL)
    private List<StayBooking> staybooking;





}
