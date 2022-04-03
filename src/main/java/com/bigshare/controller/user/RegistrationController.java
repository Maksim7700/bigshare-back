package com.bigshare.controller.user;

import com.bigshare.model.user.entity.dto.UserDto;
import com.bigshare.service.user.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {

        return registrationService.register(userDto);
    }
}
