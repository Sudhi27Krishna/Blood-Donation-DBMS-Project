package com.bloodbank.backend.security.user;

import com.bloodbank.backend.model.User;
import com.bloodbank.backend.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BBUserDetailsService implements UserDetailsService {
    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userService.getUserByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return BBUserDetails.buildUserDetails(user);
    }
}
