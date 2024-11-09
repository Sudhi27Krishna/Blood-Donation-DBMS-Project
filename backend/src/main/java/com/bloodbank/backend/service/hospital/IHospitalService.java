package com.bloodbank.backend.service.hospital;

import com.bloodbank.backend.dto.HospitalDto;
import com.bloodbank.backend.model.Hospital;
import com.bloodbank.backend.request.CreateHospitalRequest;

import java.util.List;

public interface IHospitalService {
    Hospital createHospital(CreateHospitalRequest hospitalRequest);
    Hospital getHospitalById(Long id);
    List<Hospital> getAllHospital();
    HospitalDto convertToDto(Hospital hospital);
}
