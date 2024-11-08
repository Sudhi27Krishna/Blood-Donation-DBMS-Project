package com.bloodbank.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BloodDonationDto {
    private Long id;
    private Integer qty;
    private LocalDateTime date;
}
