package com.calmfit.calm_fit.workout.repository;

import com.calmfit.calm_fit.workout.entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {

        List<ExerciseLog> findByUserIdAndExerciseIdOrderByLogDateDesc(Long userId, Long exerciseId);

        ExerciseLog findTopByUserIdAndExerciseIdOrderByLogDateDesc(Long userId, Long exerciseId);

        ExerciseLog findTopByUserIdAndExerciseIdOrderByWeightDesc(Long userId, Long exerciseId);

        ExerciseLog findTopByUserIdAndExerciseIdOrderByLogDateAsc(Long userId, Long exerciseId);

        Optional<ExerciseLog> findTopByUserIdAndExerciseIdAndRepsOrderByLogDateDesc(
                        Long userId,
                        Long exerciseId,
                        Integer reps);

        @Query("""
                        SELECT wl FROM ExerciseLog wl
                        WHERE wl.userId = :userId
                        ORDER BY wl.exercise.id, wl.logDate DESC
                        """)
        List<ExerciseLog> findAllLogsForUser(Long userId);

        Optional<ExerciseLog> findByUserIdAndExerciseIdAndRepsAndLogDate(
                        Long userId,
                        Long exerciseId,
                        Integer reps,
                        LocalDate logDate);
}