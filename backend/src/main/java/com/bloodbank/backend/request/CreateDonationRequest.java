package com.bloodbank.backend.request;

public record CreateDonationRequest(
        Integer qty,
        Long donorId
) {

}
