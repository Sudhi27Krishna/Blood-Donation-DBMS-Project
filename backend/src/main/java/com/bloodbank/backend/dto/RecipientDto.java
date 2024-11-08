package com.bloodbank.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipientDto {
    private Long id;
    private String bloodType;
    private LocalDateTime lastRequestDate;
    private List<BloodRequestDto> bloodRequestList;
}
