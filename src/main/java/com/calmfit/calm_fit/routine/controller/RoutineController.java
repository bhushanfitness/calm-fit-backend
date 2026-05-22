package com.calmfit.calm_fit.routine.controller;

import com.calmfit.calm_fit.routine.dto.RoutineDayTableResponse;
import com.calmfit.calm_fit.routine.service.RoutineService;
import com.calmfit.calm_fit.user.User;
import com.calmfit.calm_fit.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;
    private final UserRepository userRepository;

    @GetMapping("/day/{dayNumber}")
    public RoutineDayTableResponse getDayRoutineTable(
            @PathVariable Integer dayNumber,
            Authentication authentication) {
        System.out.println("🔥 CONTROLLER HIT 🔥");
        Long userId = null;

        // ✅ If user is logged in, fetch userId
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getName())) {

            User user = userRepository
                    .findByEmail(authentication.getName())
                    .orElse(null);

            if (user != null) {
                userId = user.getId();
            }
        }

        // ✅ If not logged in, userId remains null
        return routineService.getRoutineDayTable(userId, dayNumber);
    }
}