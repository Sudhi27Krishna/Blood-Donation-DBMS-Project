package com.bloodbank.backend.request;

public record CreateDistributionRequest(
        String bloodType,
        Integer qty,
        Long hospitalId
) {
}
