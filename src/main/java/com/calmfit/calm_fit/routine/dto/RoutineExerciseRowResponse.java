package com.calmfit.calm_fit.routine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineExerciseRowResponse {
    private Long exerciseId;
    private String name;
    private String muscleGroup;

    private Double wt8;
    private Double wt10;
    private Double wt12;

    private Double strengthGainPercent;
}
