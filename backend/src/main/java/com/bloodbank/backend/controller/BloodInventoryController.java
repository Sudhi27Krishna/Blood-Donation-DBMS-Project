package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.BloodDonationDto;
import com.bloodbank.backend.dto.BloodInventoryDto;
import com.bloodbank.backend.dto.BloodRequestDto;
import com.bloodbank.backend.model.BloodDonation;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.blooddonation.IBloodDonationService;
import com.bloodbank.backend.service.bloodinventory.IBloodInventoryService;
import com.bloodbank.backend.service.bloodrequest.IBloodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class BloodInventoryController {
    private final IBloodInventoryService bloodInventoryService;
    private final IBloodDonationService bloodDonationService;
    private final IBloodRequestService bloodRequestService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllInventories(){
        try {
            List<BloodInventory> inventoryList = bloodInventoryService.getAllInventory();
            List<BloodInventoryDto> inventoryDtoList = inventoryList.stream().map(bloodInventoryService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("Inventories found", inventoryDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/donations-by-bloodType")
    public ResponseEntity<ApiResponse> getDonationListByBloodType(@RequestParam String bloodType){
        try {
            List<BloodDonation> bloodDonationList = bloodInventoryService.getDonationListByBloodType(bloodType);
            List<BloodDonationDto> bloodDonationDtoList = bloodDonationList.stream().map(bloodDonationService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("Donations list found", bloodDonationDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/requests-by-bloodType")
    public ResponseEntity<ApiResponse> getRequestListByBloodType(@RequestParam String bloodType){
        try {
            List<BloodRequest> bloodRequestList = bloodInventoryService.getRequestListByBloodType(bloodType);
            List<BloodRequestDto> bloodRequestDtoList = bloodRequestList.stream().map(bloodRequestService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("Requests list found", bloodRequestDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
