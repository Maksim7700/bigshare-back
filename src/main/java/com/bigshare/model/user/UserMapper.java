package com.bigshare.model.user;

import com.bigshare.model.user.entity.Role;
import com.bigshare.model.user.entity.User;
import com.bigshare.model.user.entity.dto.UserDto;
import com.bigshare.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registrationToEntity(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(getDefaultRoles())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();
    }

    private Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(getFreelancerRole());
        roles.add(getClientRole());
        return roles;
    }

    private Role getFreelancerRole() {
        return roleRepository.findRoleByRole(RoleName.ROLE_FREELANCER.name());
    }

    private Role getClientRole() {
        return roleRepository.findRoleByRole(RoleName.ROLE_CLIENT.name());
    }
}
