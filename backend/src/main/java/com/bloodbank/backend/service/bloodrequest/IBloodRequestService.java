package com.bloodbank.backend.service.bloodrequest;

import com.bloodbank.backend.model.BloodRequest;
import com.bloodbank.backend.request.CreateBloodRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface IBloodRequestService {
    BloodRequest createBloodRequest(CreateBloodRequest request);
    List<BloodRequest> getBloodRequestsByBloodType(String bloodType);
    List<BloodRequest> getBloodRequestsByDate(LocalDateTime dateTime);
}
