package com.calmfit.calm_fit.user;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(
            Authentication authentication) {

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {

            return ResponseEntity.ok("anonymousUser");
        }

        return ResponseEntity.ok(authentication.getName());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody User request) {

        // check existing email
        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            return ResponseEntity.badRequest()
                    .body("Email already exists");
        }

        // encode password
        request.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        // save user
        userRepository.save(request);

        return ResponseEntity.ok(
                "Signup successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User request,
            HttpServletRequest httpRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            httpRequest.getSession(true)
                    .setAttribute(
                            "SPRING_SECURITY_CONTEXT",
                            SecurityContextHolder.getContext());

            return ResponseEntity.ok(
                    "Login successful");

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request) {

        SecurityContextHolder.clearContext();

        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        return ResponseEntity.ok("Logged out successfully");
    }
}