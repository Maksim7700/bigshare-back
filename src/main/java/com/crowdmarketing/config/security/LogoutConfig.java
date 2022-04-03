package com.crowdmarketing.config.security;

import com.crowdmarketing.utils.SecurityUtils;
import com.crowdmarketing.config.jwt.JWTTokenHelper;
import com.crowdmarketing.service.auth.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class LogoutConfig {

    @Autowired
    private JWTTokenHelper tokenHelper;

    @Autowired
    private TokenService tokenService;

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            String token = SecurityUtils.getAuthorizationHeader(request);
            if (token != null) {
                Claims jwtBody = tokenHelper.getJwtBody(token);
                tokenService.addToBlacklist(jwtBody.getId());
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        };
    }
}
