package com.bloodbank.backend.service.blooddonation;

import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.request.CreateDonationRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface IBloodDonationService {
    BloodDonation createDonation(CreateDonationRequest request);
    List<BloodDonation> getDonationsByBloodType(String bloodType);
    List<BloodDonation> getDonationByDate(LocalDateTime dateTime);

}
