package com.bigshare.service.user;

import com.bigshare.converters.UserConverter;
import com.bigshare.model.responses.UserResponse;
import com.bigshare.model.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseEntity<UserResponse> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = UserConverter.convertToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    public ResponseEntity<User> getAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }


}
