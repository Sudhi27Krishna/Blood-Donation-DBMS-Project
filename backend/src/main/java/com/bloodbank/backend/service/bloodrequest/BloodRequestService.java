package com.bloodbank.backend.service.bloodrequest;

import com.bloodbank.backend.enums.RequestStatus;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.repository.BloodRequestRepository;
import com.bloodbank.backend.repository.RecipientRepository;
import com.bloodbank.backend.request.CreateBloodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BloodRequestService implements IBloodRequestService {
    private final RecipientRepository recipientRepository;
    private final BloodRequestRepository bloodRequestRepository;
    @Override
    public BloodRequest createBloodRequest(CreateBloodRequest request) {
        return recipientRepository.findById(request.recipientId())
                .map(recipient -> {
                    BloodRequest bloodRequest = new BloodRequest();
                    bloodRequest.setBloodType(request.bloodType());
                    bloodRequest.setQty(request.qty());
                    bloodRequest.setDate(LocalDateTime.now());
                    bloodRequest.setRecipient(recipient);
                    // inventory deduction
                    bloodRequest.setStatus(RequestStatus.PENDING);
                    return bloodRequestRepository.save(bloodRequest);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Recipient does not exist!"));
    }

    @Override
    public List<BloodRequest> getBloodRequestsByBloodType(String bloodType) {
        return bloodRequestRepository.findByBloodType(bloodType);
    }

    @Override
    public List<BloodRequest> getBloodRequestsByDate(LocalDateTime dateTime) {
        return bloodRequestRepository.findByDate(dateTime);
    }
}
