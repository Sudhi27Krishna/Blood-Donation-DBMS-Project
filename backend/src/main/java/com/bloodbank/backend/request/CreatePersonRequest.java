package com.bloodbank.backend.request;

public record CreatePersonRequest(
        String name,
        String gender,
        Integer age,
        String bloodType,
        String email,
        String address
) {
}
