package com.calmfit.calm_fit.routine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routine_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer dayNumber;

    @Column(nullable = false)
    private String name;
}
