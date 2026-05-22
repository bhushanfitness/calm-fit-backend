package com.calmfit.calm_fit.workout.entity;

import com.calmfit.calm_fit.routine.entity.Exercise;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "exercise_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // later this will be linked to User table
    private Long userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer reps; // 8 / 10 / 12

    @Column(nullable = false)
    private LocalDate logDate;
}
