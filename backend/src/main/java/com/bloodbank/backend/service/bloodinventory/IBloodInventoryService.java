package com.bloodbank.backend.service.bloodinventory;

import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.BloodRequest;

import java.util.List;

public interface IBloodInventoryService {
    BloodInventory acceptDonation(BloodDonation donation);
    BloodInventory acceptRequest(BloodRequest request);
    List<BloodDonation> getDonationListByBloodType(String bloodType);
    List<BloodRequest> getRequestListByBloodType(String bloodType);
    List<BloodInventory> getAllInventory();

    BloodInventory getInventory(String bloodType);
}
