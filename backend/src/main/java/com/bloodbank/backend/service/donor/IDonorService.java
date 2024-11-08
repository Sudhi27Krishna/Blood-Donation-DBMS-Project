package com.bloodbank.backend.service.donor;

import com.bloodbank.backend.dto.DonorDto;
import com.bloodbank.backend.model.Donor;

import java.util.List;
import java.util.Optional;

public interface IDonorService {

    Donor getDonorByPersonId(Long personId);

    Donor createDonor(Long personId);

    List<Donor> getAllDonors();
    List<Donor> getAllDonorsByBloodType(String bloodType);

    DonorDto convertToDto(Donor donor);
}
