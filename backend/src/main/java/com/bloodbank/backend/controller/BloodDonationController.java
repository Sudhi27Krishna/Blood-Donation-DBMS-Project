package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.BloodDonationDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.request.CreateDonationRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.blooddonation.IBloodDonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/donations")
public class BloodDonationController {
    private final IBloodDonationService bloodDonationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createDonation(@RequestBody CreateDonationRequest donationRequest){
        try {
            BloodDonation bloodDonation = bloodDonationService.createDonation(donationRequest);
            BloodDonationDto bloodDonationDto = bloodDonationService.convertToDto(bloodDonation);
            return ResponseEntity.ok(new ApiResponse("Create Donation success", bloodDonationDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDonations(){
        try {
            List<BloodDonation> bloodDonationList = bloodDonationService.getAllDonations();
            List<BloodDonationDto> bloodDonationDtoList = bloodDonationList.stream().map(bloodDonationService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodDonationDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-blood-type")
    public ResponseEntity<ApiResponse> getDonationsByBloodType(@RequestParam String bloodType){
        try {
            List<BloodDonation> bloodDonationList = bloodDonationService.getDonationsByBloodType(bloodType);
            List<BloodDonationDto> bloodDonationDtoList = bloodDonationList.stream().map(bloodDonationService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodDonationDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse> getDonationsByDate(@RequestParam LocalDate date){
        try {
            List<BloodDonation> bloodDonationList = bloodDonationService.getDonationsByDate(date);
            List<BloodDonationDto> bloodDonationDtoList = bloodDonationList.stream().map(bloodDonationService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodDonationDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
