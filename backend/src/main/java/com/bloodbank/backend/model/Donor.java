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
public class Donor {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime lastDonationDate;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "donor")
    private List<BloodDonation> donationList;
}
