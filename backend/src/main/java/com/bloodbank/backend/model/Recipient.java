package com.bloodbank.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipient {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime lastRequestDate;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "recipient")
    private List<BloodRequest> bloodRequestList;
}
