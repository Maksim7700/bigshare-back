package com.bigshare.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseEntity<String> getClient() {
        return ResponseEntity.ok("CLIENT");
    }

    public ResponseEntity<String> getFreelancer() {
        return ResponseEntity.ok("FREELANCER");
    }

    public ResponseEntity<String> getModerator() {
        return ResponseEntity.ok("MODERATOR");
    }

    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("ADMIN");
    }


}
