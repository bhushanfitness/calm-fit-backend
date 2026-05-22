package com.calmfit.calm_fit.booking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {
    private String name;
    private String phone;
    private Integer amount;
}
