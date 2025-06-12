package com.livingyourdream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {

    private int place_id;
    private String name;
    private String description;
    private String type;
    private String location;
    private String photoUrl;
}
