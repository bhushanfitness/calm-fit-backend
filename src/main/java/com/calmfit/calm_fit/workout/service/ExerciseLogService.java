package com.calmfit.calm_fit.workout.service;

import com.calmfit.calm_fit.routine.entity.Exercise;
import com.calmfit.calm_fit.routine.repository.ExerciseRepository;
import com.calmfit.calm_fit.workout.dto.ExerciseLogRequest;
import com.calmfit.calm_fit.workout.dto.ExerciseLogResponse;
import com.calmfit.calm_fit.workout.entity.ExerciseLog;
import com.calmfit.calm_fit.workout.repository.ExerciseLogRepository;
import com.calmfit.calm_fit.workout.dto.StrengthProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseLogService {

        private final ExerciseLogRepository exerciseLogRepository;
        private final ExerciseRepository exerciseRepository;

        public ExerciseLogResponse logExercise(Long userId, ExerciseLogRequest request) {

                Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                                .orElseThrow(() -> new RuntimeException("Exercise not found"));

                LocalDate today = LocalDate.now();

                ExerciseLog log = exerciseLogRepository
                                .findByUserIdAndExerciseIdAndRepsAndLogDate(
                                                userId,
                                                request.getExerciseId(),
                                                request.getReps(),
                                                today)
                                .orElse(null);

                if (log == null) {

                        log = ExerciseLog.builder()
                                        .userId(userId)
                                        .exercise(exercise)
                                        .weight(request.getWeight())
                                        .reps(request.getReps())
                                        .logDate(today)
                                        .build();

                } else {

                        log.setWeight(request.getWeight());
                }

                ExerciseLog saved = exerciseLogRepository.save(log);

                return ExerciseLogResponse.builder()
                                .id(saved.getId())
                                .exerciseId(saved.getExercise().getId())
                                .weight(saved.getWeight())
                                .reps(saved.getReps())
                                .logDate(saved.getLogDate())
                                .build();
        }

        public ExerciseLogResponse getLastLog(Long userId, Long exerciseId) {

                ExerciseLog log = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByLogDateDesc(userId,
                                exerciseId);

                if (log == null) {
                        return null;
                }

                return ExerciseLogResponse.builder()
                                .id(log.getId())
                                .exerciseId(log.getExercise().getId())
                                .weight(log.getWeight())
                                .reps(log.getReps())
                                .logDate(log.getLogDate())
                                .build();
        }

        public List<ExerciseLogResponse> getHistory(Long userId, Long exerciseId) {

                return exerciseLogRepository.findByUserIdAndExerciseIdOrderByLogDateDesc(userId, exerciseId)
                                .stream()
                                .map(log -> ExerciseLogResponse.builder()
                                                .id(log.getId())
                                                .exerciseId(log.getExercise().getId())
                                                .weight(log.getWeight())
                                                .reps(log.getReps())
                                                .logDate(log.getLogDate())
                                                .build())
                                .toList();
        }

        public StrengthProgressResponse getStrengthProgress(Long userId, Long exerciseId) {

                ExerciseLog first = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByLogDateAsc(userId,
                                exerciseId);
                ExerciseLog last = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByLogDateDesc(userId,
                                exerciseId);
                ExerciseLog best = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByWeightDesc(userId,
                                exerciseId);

                if (first == null || last == null) {
                        return StrengthProgressResponse.builder()
                                        .exerciseId(exerciseId)
                                        .build();
                }

                double firstWeight = first.getWeight();
                double lastWeight = last.getWeight();
                double bestWeight = best != null ? best.getWeight() : lastWeight;

                double percentIncrease = 0;
                if (firstWeight > 0) {
                        percentIncrease = ((lastWeight - firstWeight) / firstWeight) * 100;
                }

                return StrengthProgressResponse.builder()
                                .exerciseId(exerciseId)
                                .firstWeight(firstWeight)
                                .lastWeight(lastWeight)
                                .bestWeight(bestWeight)
                                .percentageIncrease(percentIncrease)
                                .build();
        }
}
