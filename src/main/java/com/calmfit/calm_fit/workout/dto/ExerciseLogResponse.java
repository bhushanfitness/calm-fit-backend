package com.calmfit.calm_fit.workout.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseLogResponse {
    private Long id;
    private Long exerciseId;
    private Double weight;
    private Integer reps;
    private LocalDate logDate;
}
