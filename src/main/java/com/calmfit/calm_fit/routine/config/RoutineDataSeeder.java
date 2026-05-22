package com.calmfit.calm_fit.routine.config;

import com.calmfit.calm_fit.routine.entity.Exercise;
import com.calmfit.calm_fit.routine.entity.RoutineDay;
import com.calmfit.calm_fit.routine.entity.RoutineDayExercise;
import com.calmfit.calm_fit.routine.repository.ExerciseRepository;
import com.calmfit.calm_fit.routine.repository.RoutineDayExerciseRepository;
import com.calmfit.calm_fit.routine.repository.RoutineDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutineDataSeeder implements CommandLineRunner {

        private final ExerciseRepository exerciseRepository;
        private final RoutineDayRepository routineDayRepository;
        private final RoutineDayExerciseRepository routineDayExerciseRepository;

        @Override
        public void run(String... args) {
                if (routineDayRepository.count() > 0) {
                        return; // already seeded
                }

                RoutineDay day1 = routineDayRepository.save(
                                RoutineDay.builder().dayNumber(1).name("Day 1").build());
                RoutineDay day2 = routineDayRepository.save(
                                RoutineDay.builder().dayNumber(2).name("Day 2").build());
                RoutineDay day3 = routineDayRepository.save(
                                RoutineDay.builder().dayNumber(3).name("Day 3").build());
                RoutineDay day4 = routineDayRepository.save(
                                RoutineDay.builder().dayNumber(4).name("Day 4").build());
                RoutineDay day5 = routineDayRepository.save(
                                RoutineDay.builder().dayNumber(5).name("Day 5").build());

                addExerciseToDay(day1, 1, "Bench Press", "Chest");
                addExerciseToDay(day1, 2, "Incline DB Press", "Chest");
                addExerciseToDay(day1, 3, "Shoulder Press", "Shoulder");
                addExerciseToDay(day1, 4, "Lateral Raise", "Shoulder");
                addExerciseToDay(day1, 5, "Tricep Pushdown", "Triceps");
                addExerciseToDay(day1, 6, "Overhead Extension", "Triceps");

                addExerciseToDay(day2, 1, "Pull Ups", "Back");
                addExerciseToDay(day2, 2, "Lat Pulldown", "Back");
                addExerciseToDay(day2, 3, "Barbell Row", "Back");
                addExerciseToDay(day2, 4, "Barbell Curl", "Biceps");
                addExerciseToDay(day2, 5, "Hammer Curl", "Biceps");
                addExerciseToDay(day2, 6, "Rear Delt Fly", "Rear Delt");

                addExerciseToDay(day3, 1, "Squat", "Legs");
                addExerciseToDay(day3, 2, "Leg Press", "Legs");
                addExerciseToDay(day3, 3, "Leg Extension", "Legs");
                addExerciseToDay(day3, 4, "Leg Curl", "Legs");
                addExerciseToDay(day3, 5, "Standing Calf Raise", "Calves");
                addExerciseToDay(day3, 6, "Seated Calf Raise", "Calves");

                addExerciseToDay(day4, 1, "Chest Fly", "Chest");
                addExerciseToDay(day4, 2, "Dips", "Chest");
                addExerciseToDay(day4, 3, "Seated Row", "Back");
                addExerciseToDay(day4, 4, "Face Pull", "Rear Delt");
                addExerciseToDay(day4, 5, "Dumbbell Shoulder Press", "Shoulder");
                addExerciseToDay(day4, 6, "Shrugs", "Traps");

                addExerciseToDay(day5, 1, "Deadlift", "Legs");
                addExerciseToDay(day5, 2, "Leg Extension", "Legs");
                addExerciseToDay(day5, 3, "Leg Curl", "Legs");
                addExerciseToDay(day5, 4, "Preacher Curl", "Biceps");
                addExerciseToDay(day5, 5, "Incline Curl", "Biceps");
                addExerciseToDay(day5, 6, "Tricep Rope Pushdown", "Triceps");
        }

        private void addExerciseToDay(RoutineDay day, int order, String name, String muscleGroup) {

                Exercise exercise = exerciseRepository.findByNameIgnoreCase(name)
                                .orElseGet(() -> exerciseRepository.save(
                                                Exercise.builder()
                                                                .name(name)
                                                                .muscleGroup(muscleGroup)
                                                                .build()));

                routineDayExerciseRepository.save(
                                RoutineDayExercise.builder()
                                                .routineDay(day)
                                                .exercise(exercise)
                                                .exerciseOrder(order)
                                                .sets(3)
                                                .build());
        }
}
