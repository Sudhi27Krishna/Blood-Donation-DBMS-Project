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

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private BloodInventory inventory;
}
