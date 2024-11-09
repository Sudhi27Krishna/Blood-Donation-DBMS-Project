package com.bloodbank.backend.dto;

import com.bloodbank.backend.model.BloodDistribution;
import lombok.Data;

import java.util.List;

@Data
public class HospitalDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private List<BloodDistribution> bloodDistributionList;
}
