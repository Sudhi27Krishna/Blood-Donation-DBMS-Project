package com.bloodbank.backend.service.blooddonation;

import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.repository.BloodDonationRepository;
import com.bloodbank.backend.repository.DonorRepository;
import com.bloodbank.backend.request.CreateDonationRequest;
import com.bloodbank.backend.service.bloodinventory.BloodInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BloodDonationService implements IBloodDonationService {
    private final DonorRepository donorRepository;
    private final BloodDonationRepository bloodDonationRepository;
    private final BloodInventoryService bloodInventoryService;

    @Override
    public BloodDonation createDonation(CreateDonationRequest request) {
        return donorRepository.findById(request.donorId())
                .map(donor -> {
                    BloodDonation donation = new BloodDonation();
                    donation.setBloodType(request.bloodType());
                    donation.setDate(LocalDateTime.now());
                    donation.setQty(request.qty());
                    donation.setDonor(donor);
                    // inventory addition
                    BloodInventory bloodInventory = bloodInventoryService.acceptDonation(donation);
                    donation.setInventory(bloodInventory);
                    donor.getDonationList().add(donation);
                    return bloodDonationRepository.save(donation);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Donor does not exist!"));
    }
    @Override
    public List<BloodDonation> getDonationsByBloodType(String bloodType) {
        return bloodDonationRepository.findAllByBloodType(bloodType);
    }

    @Override
    public List<BloodDonation> getDonationByDate(LocalDateTime dateTime) {
        return bloodDonationRepository.findAllByDate(dateTime);
    }
}
