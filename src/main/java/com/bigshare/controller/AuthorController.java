package com.bigshare.controller;

import com.bigshare.dtos.AuthorDTO;
import com.bigshare.model.author.Author;
import com.bigshare.requests.AuthorRequest;
import com.bigshare.service.AuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/authors")
@SecurityRequirement(name = "Authorization")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBlogAuthor(@ModelAttribute AuthorRequest authorRequest) {
        try {
            Author savedAuthor = authorService.createAuthor(authorRequest);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
