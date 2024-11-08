package com.bloodbank.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DonorDto {
    private Long id;
    private LocalDateTime lastDonationDate;
    private String bloodType;
    private List<BloodDonationDto> donationList;
}
