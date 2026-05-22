package com.calmfit.calm_fit.workout.dto;

public class StrengthResponse {
    private double totalGain;
    private boolean hasEnoughData;

    public StrengthResponse(double totalGain, boolean hasEnoughData) {
        this.totalGain = totalGain;
        this.hasEnoughData = hasEnoughData;
    }

    public double getTotalGain() {
        return totalGain;
    }

    public boolean isHasEnoughData() {
        return hasEnoughData;
    }
}