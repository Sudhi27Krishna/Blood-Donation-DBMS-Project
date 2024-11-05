package com.bloodbank.backend.service.blooddistribution;

import com.bloodbank.backend.model.BloodDistribution;
import com.bloodbank.backend.request.CreateDistributionRequest;

import java.util.List;

public interface IBloodDistribution {
    BloodDistribution createDistribution(CreateDistributionRequest request);
    BloodDistribution getDistributionById(Long id);
    List<BloodDistribution> getAllDistributions();
}
