package com.bloodbank.backend.service.hospital;

import com.bloodbank.backend.model.BloodDistribution;
import com.bloodbank.backend.model.Hospital;
import com.bloodbank.backend.request.CreateHospitalRequest;

import java.util.List;

public interface IHospitalService {
    Hospital createHospital(CreateHospitalRequest hospitalRequest);
    Hospital getHospitalById(Long id);
    List<Hospital> getAllHospital();
    List<BloodDistribution> getDistributionByHospitalId(Long id);
}
