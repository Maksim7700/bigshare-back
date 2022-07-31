package com.bigshare.service.user;

import com.bigshare.exceptions.user.UsernameException;
import com.bigshare.model.user.UserMapper;
import com.bigshare.model.user.entity.dto.UserDto;
import com.bigshare.repository.UserRepository;
import com.bigshare.exceptions.email.EmailException;
import com.bigshare.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public boolean isExistUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean isExistEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public ResponseEntity<String> register(UserDto userDto) {
        if (isExistUsername(userDto.getUsername())) {
            throw new UsernameException("User with this username already exists.");
        }
        if (isExistEmail(userDto.getEmail())) {
            throw new EmailException("User with this email already exists.");
        }

        User user = userMapper.registrationToEntity(userDto);
        userRepository.save(user);
        return ResponseEntity.ok(HttpStatus.OK.toString());
    }

}
