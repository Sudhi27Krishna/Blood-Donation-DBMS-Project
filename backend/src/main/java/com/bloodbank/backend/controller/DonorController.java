package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.DonorDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.donor.IDonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("persons/donors")
public class DonorController {
    private final IDonorService donorService;

    @GetMapping("/{personId}/donor")
    public ResponseEntity<ApiResponse> getDonorByPersonId(@PathVariable Long personId){
        try {
            Donor donor = donorService.getDonorByPersonId(personId);
            DonorDto donorDto = donorService.convertToDto(donor);
            return ResponseEntity.ok(new ApiResponse("Donor found", donorDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDonors(){
        try {
            List<Donor> donorList = donorService.getAllDonors();
            List<DonorDto> donorDtoList = donorList.stream().map(donorService :: convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", donorDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-blood-type")
    public ResponseEntity<ApiResponse> getAllDonorsByBloodType(@RequestParam String bloodType){
        try {
            List<Donor> donorList = donorService.getAllDonorsByBloodType(bloodType);
            List<DonorDto> donorDtoList = donorList.stream().map(donorService :: convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", donorDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{personId}/create")
    public ResponseEntity<ApiResponse> createPerson(@PathVariable Long personId){
        try {
            Donor donor = donorService.createDonor(personId);
            DonorDto donorDto = donorService.convertToDto(donor);
            return ResponseEntity.ok(new ApiResponse("Create donor success", donorDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
