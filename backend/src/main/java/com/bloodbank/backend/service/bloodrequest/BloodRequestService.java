package com.bloodbank.backend.service.bloodrequest;

import com.bloodbank.backend.dto.BloodRequestDto;
import com.bloodbank.backend.enums.RequestStatus;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodInventory;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.repository.BloodRequestRepository;
import com.bloodbank.backend.request.CreateBloodRequest;
import com.bloodbank.backend.service.bloodinventory.BloodInventoryService;
import com.bloodbank.backend.service.person.IPersonService;
import com.bloodbank.backend.service.recipient.IRecipientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodRequestService implements IBloodRequestService {
    private final IRecipientService recipientService;
    private final IPersonService personService;
    private final BloodRequestRepository bloodRequestRepository;
    private final BloodInventoryService bloodInventoryService;
    private final ModelMapper modelMapper;

    @Override
    public BloodRequest createBloodRequest(CreateBloodRequest request) {
        return Optional.ofNullable(recipientService.getRecipientById(request.recipientId()))
                .map(recipient -> {
                    BloodRequest bloodRequest = new BloodRequest();
                    String bloodType = personService.getPersonById(recipient.getPerson().getId()).getBloodType();
                    bloodRequest.setBloodType(bloodType);
                    bloodRequest.setQty(request.qty());
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    bloodRequest.setDate(currentDateTime);
                    bloodRequest.setRecipient(recipient);
                    // inventory deduction
//                    BloodInventory bloodInventory = bloodInventoryService.acceptRequest(bloodRequest);
                    BloodInventory bloodInventory = bloodInventoryService.getInventory(bloodType);
                    bloodRequest.setInventory(bloodInventory);
                    bloodRequest.setStatus(RequestStatus.PENDING);
                    recipient.setLastRequestDate(currentDateTime);
                    recipient.getBloodRequestList().add(bloodRequest);
                    return bloodRequestRepository.save(bloodRequest);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Recipient does not exist!"));
    }

    @Override
    public List<BloodRequest> getBloodRequestsByBloodType(String bloodType) {
        return bloodRequestRepository.findByBloodType(bloodType);
    }

    @Override
    public List<BloodRequest> getBloodRequestsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();  // Start of the given day
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();  // Start of the next day (exclusive)
        return bloodRequestRepository.findAllByDateBetween(startOfDay, endOfDay);
    }

    @Override
    public List<BloodRequest> getAllBloodRequests(){
        return bloodRequestRepository.findAll();
    }

    @Override
    public BloodRequest getBloodRequestById(Long id){
        return bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));
    }

    // ADMIN access only
    @Override
    public BloodRequest acceptBloodRequest(Long id){
        BloodRequest bloodRequest = getBloodRequestById(id);
        BloodInventory bloodInventory = bloodInventoryService.acceptRequest(bloodRequest);
        bloodRequest.setInventory(bloodInventory);
        bloodRequest.setStatus(RequestStatus.FULFILLED);
        return bloodRequestRepository.save(bloodRequest);
    }

    // ADMIN access only
    @Override
    public BloodRequest rejectBloodRequest(Long id){
        BloodRequest bloodRequest = getBloodRequestById(id);
        bloodRequest.setStatus(RequestStatus.DENIED);
        return bloodRequestRepository.save(bloodRequest);
    }

    @Override
    public BloodRequestDto convertToDto(BloodRequest bloodRequest) {
        return modelMapper.map(bloodRequest, BloodRequestDto.class);
    }
}
