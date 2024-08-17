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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (
                IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping("/{name}")
    public ResponseEntity<?>  getAuthor(@PathVariable("name") String name){
        return authorService.getAuthorByName(name);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
