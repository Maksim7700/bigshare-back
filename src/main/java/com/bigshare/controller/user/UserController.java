package com.bigshare.controller.user;

import com.bigshare.model.user.entity.User;
import com.bigshare.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "Authorization")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/panel")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<User> getModerator(HttpServletRequest request) {
        return userService.getUser();
    }


}
