package com.bloodbank.backend.dto;

import com.bloodbank.backend.enums.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BloodRequestDto {
    private Long id;
    private LocalDateTime date;
    private Integer qty;
    private RequestStatus status;
}
