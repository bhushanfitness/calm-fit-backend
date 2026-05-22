package com.calmfit.calm_fit.booking.repository;

import com.calmfit.calm_fit.booking.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
}
