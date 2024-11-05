package com.bloodbank.backend.service.bloodinventory;

import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.repository.BloodInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodInventoryService implements IBloodInventoryService {
    private final BloodInventoryRepository bloodInventoryRepository;

    @Override
    public BloodInventory acceptDonation(BloodDonation donation) {
        return Optional.of(getInventory(donation.getBloodType()))
                .map(bloodInventory -> {
                    bloodInventory.acceptDonation(donation);
                    return bloodInventoryRepository.save(bloodInventory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Respository of " + donation.getBloodType() + " does not exist!"));
    }

    @Override
    public BloodInventory acceptRequest(BloodRequest request) {
        return Optional.of(getInventory(request.getBloodType()))
                .map(bloodInventory -> {
                    bloodInventory.acceptRequest(request);
                    return bloodInventoryRepository.save(bloodInventory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Respository of " + request.getBloodType() + " does not exist!"));
    }

    @Override
    public List<BloodDonation> getDonationListByBloodType(String bloodType) {
        return Optional.of(bloodInventoryRepository.findById(bloodType))
                .map(bloodInventory -> bloodInventory.get().getBloodDonationList())
                .orElseThrow(() -> new ResourceNotFoundException("Respository of " + bloodType + " does not exist!"));
    }

    @Override
    public List<BloodRequest> getRequestListByBloodType(String bloodType) {
        return Optional.of(bloodInventoryRepository.findById(bloodType))
                .map(bloodInventory -> bloodInventory.get().getBloodRequestList())
                .orElseThrow(() -> new ResourceNotFoundException("Respository of " + bloodType + " does not exist!"));
    }

    @Override
    public List<BloodInventory> getAllInventory() {
        return bloodInventoryRepository.findAll();
    }

    @Override
    public BloodInventory getInventory(String bloodType){
        return bloodInventoryRepository.findById(bloodType)
                .orElseGet(() -> {
                    BloodInventory bloodInventory = new BloodInventory();
                    bloodInventory.setBloodType(bloodType);
                    return bloodInventoryRepository.save(bloodInventory);
                });
    }
}
