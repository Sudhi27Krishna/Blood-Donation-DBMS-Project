package com.bloodbank.backend.service.user;

import com.bloodbank.backend.dto.UserDto;
import com.bloodbank.backend.model.User;
import com.bloodbank.backend.request.CreateUserRequest;

public interface IUserService {
    User createUser(CreateUserRequest request);
    User getUserById(Long id);

    UserDto convertToDto(User user);
}
