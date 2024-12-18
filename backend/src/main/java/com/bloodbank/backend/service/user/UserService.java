package com.bloodbank.backend.service.user;

import com.bloodbank.backend.dto.UserDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Role;
import com.bloodbank.backend.model.User;
import com.bloodbank.backend.repository.RoleRepository;
import com.bloodbank.backend.repository.UserRepository;
import com.bloodbank.backend.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserRequest request) {
        Role userRoles = roleRepository.findByName("ROLE_USER");
        System.out.println(userRoles);
        return Optional.of(request)
                .filter(req -> !userRepository.existsByUsername(req.username()))
                .map(req -> {
                    User user = new User();
                    user.setUsername(request.username());
                    user.setPassword(passwordEncoder.encode(request.password()));
                    user.setRoles(Set.of(userRoles));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("Oops! " + request.username() + " already exists!"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
