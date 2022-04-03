package com.bigshare;

import com.bigshare.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean("uService")
    public UserService userService() {
        return mock(UserService.class);
    }
}