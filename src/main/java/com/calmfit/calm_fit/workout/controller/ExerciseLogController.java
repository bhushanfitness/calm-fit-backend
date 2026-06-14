package com.calmfit.calm_fit.workout.controller;

import com.calmfit.calm_fit.user.User;
import com.calmfit.calm_fit.user.UserRepository;
import com.calmfit.calm_fit.workout.dto.ExerciseLogRequest;
import com.calmfit.calm_fit.workout.dto.ExerciseLogResponse;
import com.calmfit.calm_fit.workout.dto.StrengthProgressResponse;
import com.calmfit.calm_fit.workout.dto.StrengthResponse;
import com.calmfit.calm_fit.workout.service.ExerciseLogService;
import com.calmfit.calm_fit.workout.service.StrengthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout")
@RequiredArgsConstructor
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;
    private final UserRepository userRepository;
    private final StrengthService strengthService;

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }

    @PostMapping("/log")
    public ExerciseLogResponse logExercise(@RequestBody ExerciseLogRequest request) {

        Long userId = getCurrentUserId();

        return exerciseLogService.logExercise(userId, request);
    }

    @GetMapping("/last")
    public ExerciseLogResponse getLastLog(@RequestParam Long exerciseId) {

        Long userId = getCurrentUserId();
        return exerciseLogService.getLastLog(userId, exerciseId);
    }

    @GetMapping("/history")
    public List<ExerciseLogResponse> getHistory(@RequestParam Long exerciseId) {

        Long userId = getCurrentUserId();
        return exerciseLogService.getHistory(userId, exerciseId);
    }

    @GetMapping("/progress")
    public StrengthProgressResponse getProgress(@RequestParam Long exerciseId) {

        Long userId = getCurrentUserId();
        return exerciseLogService.getStrengthProgress(userId, exerciseId);
    }

    @GetMapping("/strength")
    public StrengthResponse getStrength() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return strengthService.calculateStrength(user.getId());
    }
}