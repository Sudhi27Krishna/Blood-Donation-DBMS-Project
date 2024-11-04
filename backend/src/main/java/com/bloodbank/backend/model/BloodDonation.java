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
public class BloodDonation {
    @Id
    @GeneratedValue
    private Long id;
    private String bloodType;
    private LocalDateTime date;
    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToMany
    @JoinTable(name = "donation_inventory",  joinColumns = @JoinColumn(name = "donation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id", referencedColumnName = "bloodType")
    )
    private List<BloodInventory> inventory;
}
