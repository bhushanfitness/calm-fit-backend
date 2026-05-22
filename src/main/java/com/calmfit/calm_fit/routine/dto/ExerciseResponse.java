package com.calmfit.calm_fit.routine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseResponse {
    private Long id;
    private String name;
    private String muscleGroup;
    private Integer order;
    private Integer sets;
}
