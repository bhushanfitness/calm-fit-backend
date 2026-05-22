package com.calmfit.calm_fit.booking.controller;

import com.calmfit.calm_fit.booking.dto.CreateBookingRequest;
import com.calmfit.calm_fit.booking.dto.CreateBookingResponse;
import com.calmfit.calm_fit.booking.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public CreateBookingResponse createBooking(@RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }
}
