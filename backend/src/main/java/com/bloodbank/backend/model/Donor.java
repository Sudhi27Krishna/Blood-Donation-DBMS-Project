package com.bloodbank.backend.model;

import com.bloodbank.backend.enums.BloodType;
import com.bloodbank.backend.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String name;
    private Gender gender;
    private Integer age;
    private BloodType bloodType;
    private Long contact;
    private String address;
    private LocalDateTime lastDonationDate;

    @OneToMany(mappedBy = "donor")
    private List<BloodDonation> donationList;
}
