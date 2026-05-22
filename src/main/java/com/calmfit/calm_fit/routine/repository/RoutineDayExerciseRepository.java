package com.calmfit.calm_fit.routine.repository;

import com.calmfit.calm_fit.routine.entity.RoutineDay;
import com.calmfit.calm_fit.routine.entity.RoutineDayExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineDayExerciseRepository extends JpaRepository<RoutineDayExercise, Long> {
    List<RoutineDayExercise> findByRoutineDayOrderByExerciseOrder(RoutineDay routineDay);

    List<RoutineDayExercise> findByRoutineDayId(Long routineDayId);
}
