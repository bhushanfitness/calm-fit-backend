package com.calmfit.calm_fit.routine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routine_day_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDayExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "routine_day_id")
    private RoutineDay routineDay;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private Integer exerciseOrder;

    @Column(nullable = false)
    private Integer sets;
}
