package com.bigshare.enums;

public enum RoleName {

    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    final String role;

    RoleName(String role) {
        this.role = role;
    }
}
