package com.calmfit.calm_fit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBookingResponse {
    private Long bookingId;
    private String status;
}
