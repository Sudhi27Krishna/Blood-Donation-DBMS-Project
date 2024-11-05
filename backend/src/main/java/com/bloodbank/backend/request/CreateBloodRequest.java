package com.bloodbank.backend.request;

import java.time.LocalDateTime;

public record CreateBloodRequest(
        String bloodType,
        Integer qty,
        Long recipientId
) {
}
