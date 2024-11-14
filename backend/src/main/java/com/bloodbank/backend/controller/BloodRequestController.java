package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.BloodRequestDto;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.request.CreateBloodRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.bloodrequest.IBloodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class BloodRequestController {
    private final IBloodRequestService bloodRequestService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createRequest(@RequestBody CreateBloodRequest request){
        try {
            BloodRequest bloodRequest = bloodRequestService.createBloodRequest(request);
            BloodRequestDto bloodRequestDto = bloodRequestService.convertToDto(bloodRequest);
            return ResponseEntity.ok(new ApiResponse("Create Request success", bloodRequestDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRequests(){
        try {
            List<BloodRequest> bloodRequestList = bloodRequestService.getAllBloodRequests();
            List<BloodRequestDto> bloodRequestDtoList = bloodRequestList.stream().map(bloodRequestService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodRequestDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-blood-type")
    public ResponseEntity<ApiResponse> getRequestsByBloodType(@RequestParam String bloodType){
        try {
            List<BloodRequest> bloodRequestList = bloodRequestService.getBloodRequestsByBloodType(bloodType);
            List<BloodRequestDto> bloodRequestDtoList = bloodRequestList.stream().map(bloodRequestService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodRequestDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse> getRequestsByDate(@RequestParam LocalDate date){
        try {
            List<BloodRequest> bloodRequestList = bloodRequestService.getBloodRequestsByDate(date);
            List<BloodRequestDto> bloodRequestDtoList = bloodRequestList.stream().map(bloodRequestService::convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", bloodRequestDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/accept-blood-request")
    public ResponseEntity<ApiResponse> acceptBloodRequest(@RequestParam Long id){
        try {
            BloodRequest bloodRequest = bloodRequestService.acceptBloodRequest(id);
            BloodRequestDto bloodRequestDto = bloodRequestService.convertToDto(bloodRequest);
            return ResponseEntity.ok(new ApiResponse("Request Fulfilled", bloodRequestDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/reject-blood-request")
    public ResponseEntity<ApiResponse> rejectBloodRequest(@RequestParam Long id){
        try {
            BloodRequest bloodRequest = bloodRequestService.rejectBloodRequest(id);
            BloodRequestDto bloodRequestDto = bloodRequestService.convertToDto(bloodRequest);
            return ResponseEntity.ok(new ApiResponse("Request Denied", bloodRequestDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
