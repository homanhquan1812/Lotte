package com.example.lotte.security.userDetails;

import com.example.lotte.enums.user.Role;
import com.example.lotte.projection.UserProjection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Role role;

    /**
     * If you have ROLE (e.g. ADMIN, USER), add prefix "ROLE_".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public static CustomUserDetails from(UserProjection projection) {
        return CustomUserDetails.builder()
                .id(projection.getId())
                .username(projection.getUsername())
                .password(projection.getPassword())
                .role(projection.getRole())
                .build();
    }
}
