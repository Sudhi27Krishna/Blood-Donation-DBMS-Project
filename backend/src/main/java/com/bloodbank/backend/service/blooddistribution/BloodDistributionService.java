package com.bloodbank.backend.service.blooddistribution;

import com.bloodbank.backend.dto.BloodDistributionDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDistribution;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.Hospital;
import com.bloodbank.backend.repository.BloodDistributionRepository;
import com.bloodbank.backend.request.CreateDistributionRequest;
import com.bloodbank.backend.service.bloodinventory.BloodInventoryService;
import com.bloodbank.backend.service.hospital.HospitalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodDistributionService implements IBloodDistributionService {
    private final HospitalService hospitalService;
    private final BloodInventoryService bloodInventoryService;
    private final BloodDistributionRepository bloodDistributionRepository;
    private final ModelMapper modelMapper;

    @Override
    public BloodDistribution createDistribution(CreateDistributionRequest request) {
        return Optional.ofNullable(hospitalService.getHospitalById(request.hospitalId()))
                .map(hospital -> {
                    BloodInventory inventory = bloodInventoryService.getInventory(request.bloodType());
                    if(inventory.getAvailableQty() < request.qty()){
                        throw new ResourceNotFoundException("Not enough blood quantity in inventory!");
                    }
                    BloodDistribution distribution = new BloodDistribution();
                    distribution.setBloodType(request.bloodType());
                    distribution.setQty(request.qty());
                    distribution.setDistributedDate(LocalDateTime.now());
                    distribution.setHospital(hospital);
                    hospital.getBloodDistributionList().add(distribution);
                    // inventory deduction
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

    @Override
    public List<BloodDistribution> getDistributionsByHospitalId(Long hospitalId) {
        return Optional.ofNullable(hospitalService.getHospitalById(hospitalId))
                .map(Hospital::getBloodDistributionList)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found"));
    }

    @Override
    public BloodDistributionDto convertToDto(BloodDistribution bloodDistribution){
        return modelMapper.map(bloodDistribution, BloodDistributionDto.class);
    }
}
