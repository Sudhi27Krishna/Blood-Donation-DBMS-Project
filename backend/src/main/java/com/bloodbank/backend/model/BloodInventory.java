package com.bloodbank.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BloodInventory {
    @Id
    @GeneratedValue
    private String bloodType;
    private Integer availableQty;

    @ManyToMany(mappedBy = "inventory")
    private List<BloodDonation> bloodDonationList;

    @ManyToMany(mappedBy = "inventory")
    private List<BloodRequest> bloodRequestList;
}
