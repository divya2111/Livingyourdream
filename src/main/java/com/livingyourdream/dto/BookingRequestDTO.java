package com.livingyourdream.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.livingyourdream.Enum.BookingStatus.BookingStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequestDTO {

    private LocalDate booking_date;
    private BookingStatus status;
    private double total_cost;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private int NumOfPeople;
    private Long user_id;
    private Long plan_id;
    private Long customplan_id;
    private Long payment_id;
}
