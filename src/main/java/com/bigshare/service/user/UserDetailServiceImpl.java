package com.bigshare.service.user;

import com.bigshare.model.requests.AuthenticationRequest;
import com.bigshare.model.responses.LoginResponse;
import com.bigshare.repository.user.UserRepository;
import com.bigshare.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private AuthService loginService;

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        return userRepository.findByUsername(str);
    }

    public ResponseEntity<LoginResponse> login(AuthenticationRequest authenticationRequest) {

        LoginResponse loginResponse = new LoginResponse();
        String jwt = loginService.getJwt(authenticationRequest);
        loginResponse.setToken(jwt);

        return ResponseEntity.ok(loginResponse);
    }

}
