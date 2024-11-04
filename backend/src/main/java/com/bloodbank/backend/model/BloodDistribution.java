package com.bloodbank.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BloodDistribution {
    @Id
    @GeneratedValue
    private Long id;
    private String bloodType;
    private Integer qty;
    private LocalDateTime distributedDate;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
