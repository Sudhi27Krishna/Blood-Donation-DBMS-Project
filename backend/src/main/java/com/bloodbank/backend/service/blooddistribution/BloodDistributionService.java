package com.bloodbank.backend.service.blooddistribution;

import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDistribution;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.repository.BloodDistributionRepository;
import com.bloodbank.backend.request.CreateDistributionRequest;
import com.bloodbank.backend.service.bloodinventory.BloodInventoryService;
import com.bloodbank.backend.service.hospital.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodDistributionService implements IBloodDistribution {
    private final HospitalService hospitalService;
    private final BloodInventoryService bloodInventoryService;
    private final BloodDistributionRepository bloodDistributionRepository;
    @Override
    public BloodDistribution createDistribution(CreateDistributionRequest request) {
        return Optional.ofNullable(hospitalService.getHospitalById(request.hospitalId()))
                .map(hospital -> {
                    BloodDistribution distribution = new BloodDistribution();
                    distribution.setBloodType(request.bloodType());
                    distribution.setQty(request.qty());
                    distribution.setDistributedDate(LocalDateTime.now());
                    distribution.setHospital(hospital);
                    hospital.getBloodDistributionList().add(distribution);
                    // inventory deduction
                    BloodInventory inventory = bloodInventoryService.getInventory(request.bloodType());
                    inventory.deductBloodQty(request.qty());
                    return bloodDistributionRepository.save(distribution);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found!"));
    }

    @Override
    public BloodDistribution getDistributionById(Long id) {
        return bloodDistributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found!"));
    }

    @Override
    public List<BloodDistribution> getAllDistributions() {
        return bloodDistributionRepository.findAll();
    }
}
