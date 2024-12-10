package com.bigshare.config.security;

import com.bigshare.config.jwt.JWTTokenHelper;
import com.bigshare.service.TokenService;
import com.bigshare.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class LogoutConfig {

    private final JWTTokenHelper tokenHelper;
    private final TokenService tokenService;

    public LogoutConfig(JWTTokenHelper tokenHelper, TokenService tokenService) {
        this.tokenHelper = tokenHelper;
        this.tokenService = tokenService;
    }

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
