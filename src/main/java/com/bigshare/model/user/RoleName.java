package com.bigshare.model.user;

public enum RoleName {

    ROLE_FREELANCER("ROLE_FREELANCER"),
    ROLE_CLIENT("ROLE_CLIENT"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    final String role;

    RoleName(String role) {
        this.role = role;
    }
}
