package com.bigshare.controller;


import com.bigshare.dtos.BlogDTO;
import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogPostContent;
import com.bigshare.requests.BlogPostContentRequest;
import com.bigshare.requests.BlogRequest;
import com.bigshare.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/blogs")
@SecurityRequirement(name = "Authorization")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping("/{blogId}/contents")
    public ResponseEntity<BlogPostContent> addBlogPostContent(
            @PathVariable Long blogId,
            @RequestBody BlogPostContentRequest request) throws IOException {
        return blogService.addBlogPostContent(blogId, request);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BlogDTO>> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAll(page, size);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBlog(@ModelAttribute BlogRequest blog) throws IOException {
        return blogService.addBlog(blog);
    }
}
