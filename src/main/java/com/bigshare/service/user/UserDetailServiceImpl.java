package com.bigshare.service.user;

import com.bigshare.requests.AuthenticationRequest;
import com.bigshare.dtos.LoginDTO;
import com.bigshare.repository.UserRepository;
import com.bigshare.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthService loginService;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository, AuthService loginService) {
        this.userRepository = userRepository;
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        return userRepository.findByUsername(str);
    }

    public ResponseEntity<LoginDTO> login(AuthenticationRequest authenticationRequest) {

        LoginDTO loginResponse = new LoginDTO();
        String jwt = loginService.getJwt(authenticationRequest);
        loginResponse.setToken(jwt);

        return ResponseEntity.ok(loginResponse);
    }

}
