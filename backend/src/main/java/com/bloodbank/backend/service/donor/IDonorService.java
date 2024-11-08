package com.bloodbank.backend.service.donor;

import com.bloodbank.backend.dto.DonorDto;
import com.bloodbank.backend.model.Donor;

import java.util.List;

public interface IDonorService {

    Donor getDonorById(Long donorId);

    Donor getDonorByPersonId(Long personId);

    Donor createDonor(Long personId);

    List<Donor> getAllDonors();
    List<Donor> getAllDonorsByBloodType(String bloodType);

    DonorDto convertToDto(Donor donor);
}
