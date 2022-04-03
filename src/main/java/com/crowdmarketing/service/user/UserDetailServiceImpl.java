package com.crowdmarketing.service.user;

import com.crowdmarketing.model.requests.AuthenticationRequest;
import com.crowdmarketing.model.responses.LoginResponse;
import com.crowdmarketing.repository.user.UserRepository;
import com.crowdmarketing.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private AuthService loginService;

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        return userRepository.findByUsername(str);
    }

    public ResponseEntity<LoginResponse> login(AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        LoginResponse loginResponse = new LoginResponse();
        String jwt = loginService.getJwt(authenticationRequest);
        loginResponse.setToken(jwt);

        return ResponseEntity.ok(loginResponse);
    }

}
