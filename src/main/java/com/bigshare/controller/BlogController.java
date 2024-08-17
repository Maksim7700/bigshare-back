package com.bigshare.controller;


import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogPostContent;
import com.bigshare.requests.BlogPostContentRequest;
import com.bigshare.requests.BlogRequest;
import com.bigshare.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/blogs")
@SecurityRequirement(name = "Authorization")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping("/{blogId}/contents")
    public ResponseEntity<BlogPostContent> addBlogPostContent(
            @PathVariable Long blogId,
            @RequestBody BlogPostContentRequest request) throws IOException {
        return blogService.addBlogPostContent(blogId, request);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return blogService.getAll();
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> addBlog(@RequestBody BlogRequest blog) throws IOException {
        return blogService.addBlog(blog);
    }
}
