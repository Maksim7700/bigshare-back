package com.bigshare.controller.user;

import com.bigshare.model.requests.AuthenticationRequest;
import com.bigshare.service.user.UserDetailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final UserDetailServiceImpl userDetailService;

    public AuthenticationController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

        return userDetailService.login(authenticationRequest);
    }

}
