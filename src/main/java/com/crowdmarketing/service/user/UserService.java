package com.crowdmarketing.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> getClient() {
        return ResponseEntity.ok("CLIENT");
    }

    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<String> getFreelancer() {
        return ResponseEntity.ok("FREELANCER");
    }

    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> getModerator() {
        return ResponseEntity.ok("MODERATOR");
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("ADMIN");
    }


}
