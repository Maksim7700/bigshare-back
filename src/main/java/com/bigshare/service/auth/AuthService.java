package com.bigshare.service.auth;

import com.bigshare.config.jwt.JWTTokenHelper;
import com.bigshare.exceptions.invalid.InvalidUsernameOrPasswordException;
import com.bigshare.model.requests.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenHelper jWTTokenHelper;

    public AuthService(AuthenticationManager authenticationManager, JWTTokenHelper jWTTokenHelper) {
        this.authenticationManager = authenticationManager;
        this.jWTTokenHelper = jWTTokenHelper;
    }

    public String getJwt(AuthenticationRequest authenticationRequest) {
        try{
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jWTTokenHelper.generateToken(authentication, null);
        } catch (Exception e) {
            throw new InvalidUsernameOrPasswordException("Invalid password or username");
        }
    }
}
