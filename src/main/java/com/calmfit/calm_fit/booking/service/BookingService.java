package com.calmfit.calm_fit.booking.service;

import com.calmfit.calm_fit.booking.dto.CreateBookingRequest;
import com.calmfit.calm_fit.booking.dto.CreateBookingResponse;
import com.calmfit.calm_fit.booking.entity.BookingRequest;
import com.calmfit.calm_fit.booking.repository.BookingRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRequestRepository bookingRequestRepository;

    public BookingService(BookingRequestRepository bookingRequestRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
    }

    public CreateBookingResponse createBooking(CreateBookingRequest request) {

        if (request.getAmount() == null || request.getAmount() < 99) {
            throw new RuntimeException("Minimum amount is 99");
        }

        BookingRequest booking = BookingRequest.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .amount(request.getAmount())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        BookingRequest saved = bookingRequestRepository.save(booking);

        return new CreateBookingResponse(saved.getId(), saved.getStatus());
    }
}
