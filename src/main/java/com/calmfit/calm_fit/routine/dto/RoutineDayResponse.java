package com.calmfit.calm_fit.routine.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDayResponse {
    private Integer dayNumber;
    private String name;
    private List<ExerciseResponse> exercises;
}
