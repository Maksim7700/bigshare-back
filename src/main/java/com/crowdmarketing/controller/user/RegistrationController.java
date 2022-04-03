package com.crowdmarketing.controller.user;

import com.crowdmarketing.model.user.entity.dto.UserDto;
import com.crowdmarketing.service.user.RegistrationService;
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
