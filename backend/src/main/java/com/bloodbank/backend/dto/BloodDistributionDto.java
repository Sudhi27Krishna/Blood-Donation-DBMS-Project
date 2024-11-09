package com.bloodbank.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BloodDistributionDto {
    private Long id;
    private String bloodType;
    private Integer qty;
    private LocalDateTime distributedDate;
}
