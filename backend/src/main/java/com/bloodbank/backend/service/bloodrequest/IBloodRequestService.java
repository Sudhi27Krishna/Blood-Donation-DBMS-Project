package com.bloodbank.backend.service.bloodrequest;

import com.bloodbank.backend.dto.BloodRequestDto;
import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.request.CreateBloodRequest;

import java.time.LocalDate;
import java.util.List;

public interface IBloodRequestService {
    BloodRequest createBloodRequest(CreateBloodRequest request);
    List<BloodRequest> getBloodRequestsByBloodType(String bloodType);
    List<BloodRequest> getBloodRequestsByDate(LocalDate date);

    List<BloodRequest> getAllBloodRequests();

    BloodRequestDto convertToDto(BloodRequest bloodRequest);
}
