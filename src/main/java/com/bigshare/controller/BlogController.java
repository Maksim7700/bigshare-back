package com.bigshare.controller;


import com.bigshare.dtos.BlogDTO;
import com.bigshare.dtos.BlogPostContentDTO;
import com.bigshare.requests.BlogPostContentRequest;
import com.bigshare.requests.BlogRequest;
import com.bigshare.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/blogs")
@SecurityRequirement(name = "Authorization")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping("/{blogId}")
    public ResponseEntity<?> addBlogPostContent(
            @PathVariable Long blogId,
            @ModelAttribute BlogPostContentRequest request) throws IOException {
        return blogService.addBlogPostContent(blogId, request);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping("/{blogId}")
    public ResponseEntity<Page<BlogPostContentDTO>> getBlogPostContents(
            @PathVariable("blogId") Long blogId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAllBlogPostContents(blogId, page, size);
    }


    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BlogDTO>> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAll(page, size);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateBlog(
            @RequestParam Long blogId,
            @RequestParam boolean status) {
        return blogService.updateBlog(blogId, status);
    }


    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBlog(@ModelAttribute BlogRequest blog) throws IOException {
        return blogService.addBlog(blog);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> delete(@PathVariable long blogId) {
        return blogService.deleteBlog(blogId);
    }
}

