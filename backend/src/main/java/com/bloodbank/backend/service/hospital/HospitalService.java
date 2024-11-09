package com.bloodbank.backend.service.hospital;

import com.bloodbank.backend.dto.HospitalDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Hospital;
import com.bloodbank.backend.repository.HospitalRepository;
import com.bloodbank.backend.request.CreateHospitalRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HospitalService implements IHospitalService {
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    @Override
    public Hospital createHospital(CreateHospitalRequest hospitalRequest) {
        return Optional.of(hospitalRequest)
                .filter(request -> !hospitalRepository.existsByEmail(request.email()))
                .map(req -> {
                    Hospital hospital = new Hospital();
                    hospital.setName(req.name());
                    hospital.setEmail(req.email());
                    hospital.setAddress(req.address());
                    return hospitalRepository.save(hospital);
                })
                .orElseThrow(() -> new AlreadyExistsException(hospitalRequest.name() + " already exists!"));
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found!"));
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalRepository.findAll();
    }

    @Override
    public HospitalDto convertToDto(Hospital hospital){
        return modelMapper.map(hospital, HospitalDto.class);
    }
}
