package com.bloodbank.backend.service.bloodinventory;

import com.bloodbank.backend.dto.BloodInventoryDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.repository.BloodInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodInventoryService implements IBloodInventoryService {
    private final BloodInventoryRepository bloodInventoryRepository;
    private final ModelMapper modelMapper;

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
                    if(bloodInventory.getAvailableQty() < request.getQty()){
                        throw new ResourceNotFoundException("Not enough blood quantity in inventory!");
                    }
                    bloodInventory.acceptRequest(request);
                    return bloodInventoryRepository.save(bloodInventory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Respository of " + request.getBloodType() + " does not exist!"));
    }

    @Override
    public List<BloodDonation> getDonationListByBloodType(String bloodType) {
        return getInventory(bloodType).getBloodDonationList();
    }

    @Override
    public List<BloodRequest> getRequestListByBloodType(String bloodType) {
        return getInventory(bloodType).getBloodRequestList();
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

    @Override
    public BloodInventoryDto convertToDto(BloodInventory inventory){
        return modelMapper.map(inventory, BloodInventoryDto.class);
    }
}
