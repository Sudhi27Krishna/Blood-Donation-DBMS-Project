package com.bloodbank.backend.request;

public record UpdatePersonRequest(
        String name,
        Integer age,
        String email,
        String address
) {
}
