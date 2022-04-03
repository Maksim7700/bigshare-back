package com.crowdmarketing.service.user;

import com.crowdmarketing.exceptions.user.UsernameException;
import com.crowdmarketing.model.user.UserMapper;
import com.crowdmarketing.model.user.entity.dto.UserDto;
import com.crowdmarketing.repository.user.UserRepository;
import com.crowdmarketing.exceptions.email.EmailException;
import com.crowdmarketing.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
