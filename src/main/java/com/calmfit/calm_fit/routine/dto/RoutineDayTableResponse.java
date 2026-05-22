package com.calmfit.calm_fit.routine.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDayTableResponse {

    private Integer dayNumber;
    private List<RoutineExerciseRowResponse> exercises;
}
