package com.livingyourdream.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Place {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long place_id;
   private String name;
   private String description;
   private String type;
   private String city;
   private String state;
   private String country;
   private String photoUrl;


}
