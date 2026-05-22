package com.calmfit.calm_fit.workout.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLogRequest {

    private Long exerciseId;
    private Double weight;
    private Integer reps;
}