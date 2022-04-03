package com.crowdmarketing.controller.user;

import com.crowdmarketing.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "Authorization")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @GetMapping("/client")
    public ResponseEntity<String> getClient() {
        return userService.getClient();
    }

    @GetMapping("/freelancer")
    public ResponseEntity<String> getFreelancer() {
        return userService.getFreelancer();
    }

    @GetMapping("/moderator")
    public ResponseEntity<String> getModerator() {
        return userService.getModerator();
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        return userService.getAdmin();
    }

    @SneakyThrows
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutSuccessHandler.onLogoutSuccess(request,response,authentication);
        return ResponseEntity.ok("logout success");
    }
}
