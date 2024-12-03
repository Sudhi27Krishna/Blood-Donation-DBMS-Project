package com.bloodbank.backend.request;

public record CreateUserRequest(
        String username,
        String password
) {
}
