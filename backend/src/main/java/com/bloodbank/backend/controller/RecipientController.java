package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.DonorDto;
import com.bloodbank.backend.dto.RecipientDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.model.Recipient;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.recipient.IRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons/recipients")
public class RecipientController {
    private final IRecipientService recipientService;

    @GetMapping("/{personId}/recipient")
    public ResponseEntity<ApiResponse> getRecipientByPersonId(@PathVariable Long personId){
        try {
            Recipient recipient = recipientService.getRecipientByPersonId(personId);
            RecipientDto recipientDto = recipientService.convertToDto(recipient);
            return ResponseEntity.ok(new ApiResponse("Recipient found", recipientDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRecipients(){
        try {
            List<Recipient> recipientList = recipientService.getAllRecipients();
            List<RecipientDto> recipientDtoList = recipientList.stream().map(recipientService :: convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", recipientDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{bloodType}/all")
    public ResponseEntity<ApiResponse> getAllRecipientsByBloodType(@PathVariable String bloodType){
        try {
            List<Recipient> recipientList = recipientService.getAllRecipientsByBloodType(bloodType);
            List<RecipientDto> recipientDtoList = recipientList.stream().map(recipientService :: convertToDto).toList();
            return ResponseEntity.ok(new ApiResponse("List found", recipientDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{personId}/create")
    public ResponseEntity<ApiResponse> createPerson(@PathVariable Long personId){
        try {
            Recipient recipient = recipientService.createRecipient(personId);
            RecipientDto recipientDto = recipientService.convertToDto(recipient);
            return ResponseEntity.ok(new ApiResponse("Create recipient success", recipientDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
