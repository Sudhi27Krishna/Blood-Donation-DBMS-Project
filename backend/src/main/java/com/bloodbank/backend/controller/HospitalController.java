package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.HospitalDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.model.Hospital;
import com.bloodbank.backend.request.CreateHospitalRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.hospital.IHospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    private final IHospitalService hospitalService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createHospital(@RequestBody CreateHospitalRequest request){
        try {
            Hospital hospital = hospitalService.createHospital(request);
            HospitalDto hospitalDto = hospitalService.convertToDto(hospital);
            return ResponseEntity.ok(new ApiResponse("Create hospital success", hospitalDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllHospitals(){
        try {
            List<Hospital> hospitalList = hospitalService.getAllHospital();
            List<HospitalDto> hospitalDtoList = hospitalList.stream().map(hospitalService :: convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", hospitalDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
