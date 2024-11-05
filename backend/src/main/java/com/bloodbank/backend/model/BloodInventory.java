package com.bloodbank.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BloodInventory {
    @Id
    private String bloodType;
    private Integer availableQty = 0;

    @OneToMany(mappedBy = "inventory")
    private List<BloodDonation> bloodDonationList = new ArrayList<>();

    @OneToMany(mappedBy = "inventory")
    private List<BloodRequest> bloodRequestList = new ArrayList<>();

    public void acceptDonation(BloodDonation donation){
        this.availableQty += donation.getQty();
        bloodDonationList.add(donation);
    }

    public void acceptRequest(BloodRequest request){
        this.availableQty -= request.getQty();
        bloodRequestList.add(request);
    }

    public void deductBloodQty(Integer qty){
        this.availableQty -= qty;
    }
}
