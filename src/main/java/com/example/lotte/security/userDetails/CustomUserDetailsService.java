package com.example.lotte.security.userDetails;

import com.example.lotte.projection.UserProjection;
import com.example.lotte.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProjection userProjection = userRepository.findUserProjectionByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return CustomUserDetails.builder()
                .id(userProjection.getId())
                .username(userProjection.getUsername())
                .password(userProjection.getPassword())
                .role(userProjection.getRole())
                .build();
    }
}