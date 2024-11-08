package com.bloodbank.backend.request;

public record CreateBloodRequest(
        Integer qty,
        Long recipientId
) {
}
