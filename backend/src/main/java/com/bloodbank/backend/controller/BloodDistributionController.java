package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.BloodDistributionDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDistribution;
import com.bloodbank.backend.request.CreateDistributionRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.blooddistribution.IBloodDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bloodDistribution")
public class BloodDistributionController {
    private final IBloodDistributionService bloodDistributionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBloodDistribution(@RequestBody CreateDistributionRequest request){
        try {
            BloodDistribution bloodDistribution = bloodDistributionService.createDistribution(request);
            BloodDistributionDto bloodDistributionDto = bloodDistributionService.convertToDto(bloodDistribution);
            return ResponseEntity.ok(new ApiResponse("Create distribution success", bloodDistributionDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDistributionById(@PathVariable Long id){
        try {
            BloodDistribution bloodDistribution = bloodDistributionService.getDistributionById(id);
            BloodDistributionDto bloodDistributionDto = bloodDistributionService.convertToDto(bloodDistribution);
            return ResponseEntity.ok(new ApiResponse("Distribution found", bloodDistributionDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-hospital")
    public ResponseEntity<ApiResponse> getDistributionByHospitalId(@RequestParam Long hospitalId){
        try {
            List<BloodDistribution> bloodDistributionList = bloodDistributionService.getDistributionsByHospitalId(hospitalId);
            List<BloodDistributionDto> bloodDistributionDtoList = bloodDistributionList.stream().map(bloodDistributionService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodDistributionDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDistributions(){
        try {
            List<BloodDistribution> bloodDistributionList = bloodDistributionService.getAllDistributions();
            List<BloodDistributionDto> bloodDistributionDtoList = bloodDistributionList.stream().map(bloodDistributionService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodDistributionDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
