package com.bloodbank.backend.service.donor;

import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.model.Person;

import java.util.List;

public interface IDonorService {
    Donor createDonor(Person person);
    List<Donor> getAllDonors();
    List<Donor> getAllDonorsByBloodType(String bloodType);
}
