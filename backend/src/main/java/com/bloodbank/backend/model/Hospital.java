package com.bloodbank.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Hospital {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String address;

    @OneToMany(mappedBy = "hospital")
    private List<BloodDistribution> bloodDistributionList;
}
