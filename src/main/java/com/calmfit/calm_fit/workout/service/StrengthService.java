package com.calmfit.calm_fit.workout.service;

import com.calmfit.calm_fit.workout.dto.StrengthResponse;

public interface StrengthService {
    StrengthResponse calculateStrength(Long userId);
}
