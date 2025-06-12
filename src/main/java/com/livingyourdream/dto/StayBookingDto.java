package com.livingyourdream.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StayBookingDto {
    private int room_no;
    private LocalDate Date;
}
