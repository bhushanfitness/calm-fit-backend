package com.calmfit.calm_fit.workout.service.impl;

import com.calmfit.calm_fit.workout.dto.StrengthResponse;
import com.calmfit.calm_fit.workout.entity.ExerciseLog;
import com.calmfit.calm_fit.workout.repository.ExerciseLogRepository;
import com.calmfit.calm_fit.workout.service.StrengthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StrengthServiceImpl implements StrengthService {

    private final ExerciseLogRepository exerciseLogRepository;

    public StrengthServiceImpl(ExerciseLogRepository exerciseLogRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
    }

    @Override
    public StrengthResponse calculateStrength(Long userId) {

        List<ExerciseLog> logs = exerciseLogRepository.findAllLogsForUser(userId);

        Map<Long, List<ExerciseLog>> grouped = logs.stream()
                .collect(Collectors.groupingBy(l -> l.getExercise().getId()));

        List<Double> gains = new ArrayList<>();

        for (Map.Entry<Long, List<ExerciseLog>> entry : grouped.entrySet()) {

            List<ExerciseLog> exerciseLogs = entry.getValue();

            // latest first
            exerciseLogs.sort(Comparator.comparing(ExerciseLog::getLogDate).reversed());

            if (exerciseLogs.size() < 2)
                continue;

            double latestVolume = calculateVolume(exerciseLogs, 0);
            double previousVolume = calculateVolume(exerciseLogs, 1);

            if (previousVolume == 0)
                continue;

            double gain = ((latestVolume - previousVolume) / previousVolume) * 100;
            gains.add(gain);
        }

        if (gains.isEmpty()) {
            return new StrengthResponse(0.0, false);
        }

        double avgGain = gains.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return new StrengthResponse(round(avgGain), true);
    }

    private double calculateVolume(List<ExerciseLog> logs, int indexOffset) {

        LocalDate sessionDate = logs.get(indexOffset).getLogDate();

        return logs.stream()
                .filter(l -> l.getLogDate().equals(sessionDate))
                .mapToDouble(l -> l.getWeight() * l.getReps())
                .sum();
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}