package com.example.lotte.projection;

import com.example.lotte.enums.user.Role;

public interface UserProjection {
    Long getId();
    String getUsername();
    String getPassword();
    Role getRole();
}
