package com.bloodbank.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BloodInventoryDto {
    private String bloodType;
    private Integer availableQty;
    private List<BloodDonationDto> bloodDonationList;
    private List<BloodRequestDto> bloodRequestList;
}
