package com.calmfit.calm_fit.workout.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StrengthProgressResponse {

    private Long exerciseId;

    private Double firstWeight;
    private Double lastWeight;
    private Double bestWeight;

    private Double percentageIncrease;
}
