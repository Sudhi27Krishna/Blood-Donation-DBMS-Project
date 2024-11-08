package com.bloodbank.backend.service.blooddonation;

import com.bloodbank.backend.dto.BloodDonationDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.repository.BloodDonationRepository;
import com.bloodbank.backend.request.CreateDonationRequest;
import com.bloodbank.backend.service.bloodinventory.BloodInventoryService;
import com.bloodbank.backend.service.donor.IDonorService;
import com.bloodbank.backend.service.person.IPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodDonationService implements IBloodDonationService {
    private final IDonorService donorService;
    private final IPersonService personService;
    private final BloodDonationRepository bloodDonationRepository;
    private final BloodInventoryService bloodInventoryService;
    private final ModelMapper modelMapper;

    @Override
    public BloodDonation createDonation(CreateDonationRequest request) {
        return Optional.ofNullable(donorService.getDonorById(request.donorId()))
                .map(donor -> {
                    BloodDonation donation = new BloodDonation();
                    donation.setBloodType(personService.getPersonById(donor.getPerson().getId()).getBloodType());
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    donation.setDate(currentDateTime);
                    donation.setQty(request.qty());
                    donation.setDonor(donor);
                    donor.setLastDonationDate(currentDateTime);
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
    public List<BloodDonation> getDonationsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();  // Start of the given day
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();  // Start of the next day (exclusive)
        return bloodDonationRepository.findAllByDateBetween(startOfDay, endOfDay);
    }

    @Override
    public List<BloodDonation> getAllDonations(){
        return bloodDonationRepository.findAll();
    }

    @Override
    public BloodDonationDto convertToDto(BloodDonation bloodDonation){
        return modelMapper.map(bloodDonation, BloodDonationDto.class);
    }
}
