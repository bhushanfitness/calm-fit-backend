package com.calmfit.calm_fit.routine.repository;

import com.calmfit.calm_fit.routine.entity.RoutineDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutineDayRepository extends JpaRepository<RoutineDay, Long> {
    Optional<RoutineDay> findByDayNumber(Integer dayNumber);
}
