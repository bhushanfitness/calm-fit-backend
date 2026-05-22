package com.calmfit.calm_fit.routine.service;

import com.calmfit.calm_fit.routine.dto.RoutineDayTableResponse;
import com.calmfit.calm_fit.routine.dto.RoutineExerciseRowResponse;
import com.calmfit.calm_fit.routine.entity.RoutineDay;
import com.calmfit.calm_fit.routine.entity.RoutineDayExercise;
import com.calmfit.calm_fit.routine.repository.RoutineDayExerciseRepository;
import com.calmfit.calm_fit.routine.repository.RoutineDayRepository;
import com.calmfit.calm_fit.workout.entity.ExerciseLog;
import com.calmfit.calm_fit.workout.repository.ExerciseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoutineService {

        private final RoutineDayRepository routineDayRepository;
        private final RoutineDayExerciseRepository routineDayExerciseRepository;
        private final ExerciseLogRepository exerciseLogRepository;

        public RoutineDayTableResponse getRoutineDayTable(Long userId, Integer dayNumber) {

                RoutineDay routineDay = routineDayRepository.findByDayNumber(dayNumber)
                                .orElseThrow(() -> new RuntimeException("Routine day not found: " + dayNumber));

                List<RoutineDayExercise> dayExercises = routineDayExerciseRepository
                                .findByRoutineDayId(routineDay.getId());

                dayExercises.sort(Comparator.comparingInt(RoutineDayExercise::getExerciseOrder));

                List<RoutineExerciseRowResponse> rows = dayExercises.stream()
                                .map(dayEx -> buildExerciseRow(userId, dayEx))
                                .toList();

                return RoutineDayTableResponse.builder()
                                .dayNumber(dayNumber)
                                .exercises(rows)
                                .build();
        }

        private RoutineExerciseRowResponse buildExerciseRow(Long userId, RoutineDayExercise dayEx) {

                Long exerciseId = dayEx.getExercise().getId();

                Double wt8 = getLatestWeight(userId, exerciseId, 8);
                Double wt10 = getLatestWeight(userId, exerciseId, 10);
                Double wt12 = getLatestWeight(userId, exerciseId, 12);

                Double strengthGain = calculateStrengthGain(userId, exerciseId);

                return RoutineExerciseRowResponse.builder()
                                .exerciseId(exerciseId)
                                .name(dayEx.getExercise().getName())
                                .muscleGroup(dayEx.getExercise().getMuscleGroup())
                                .wt8(wt8)
                                .wt10(wt10)
                                .wt12(wt12)
                                .strengthGainPercent(strengthGain)
                                .build();
        }

        private Double getLatestWeight(Long userId, Long exerciseId, Integer reps) {

                Optional<ExerciseLog> logOpt = exerciseLogRepository
                                .findTopByUserIdAndExerciseIdAndRepsOrderByLogDateDesc(userId, exerciseId, reps);

                return logOpt.map(ExerciseLog::getWeight).orElse(null);
        }

        private Double calculateStrengthGain(Long userId, Long exerciseId) {

                ExerciseLog first = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByLogDateAsc(userId,
                                exerciseId);
                ExerciseLog last = exerciseLogRepository.findTopByUserIdAndExerciseIdOrderByLogDateDesc(userId,
                                exerciseId);

                if (first == null || last == null) {
                        return null;
                }

                double firstWeight = first.getWeight();
                double lastWeight = last.getWeight();

                if (firstWeight <= 0) {
                        return null;
                }

                return ((lastWeight - firstWeight) / firstWeight) * 100;
        }
}
