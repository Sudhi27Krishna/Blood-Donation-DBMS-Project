package com.bloodbank.backend.request;

public record CreateHospitalRequest(
        String name,
        String email,
        String address
) {
}
