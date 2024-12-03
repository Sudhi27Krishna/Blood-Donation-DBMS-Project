package com.bloodbank.backend.service.user;

import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.model.Role;
import com.bloodbank.backend.model.User;
import com.bloodbank.backend.repository.RoleRepository;
import com.bloodbank.backend.repository.UserRepository;
import com.bloodbank.backend.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(CreateUserRequest request) {
        Role userRoles = roleRepository.findByName("ROLE_USER");
        return Optional.of(request)
//                .filter(req -> !userRepository.existsByUsername(req.username()))
                .map(req -> {
                    User user = new User();
                    user.setUsername(request.username());
                    user.setPassword(request.password());
                    user.setRoles(Set.of(userRoles));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("Oops! " + request.username() + " already exists!"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found!"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
