package com.bigshare.controller;


import com.bigshare.model.blog.Author;
import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogPostContent;
import com.bigshare.model.requests.AuthorRequest;
import com.bigshare.model.requests.BlogRequest;
import com.bigshare.model.responses.AuthorDTO;
import com.bigshare.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            @RequestBody BlogPostContent request) {

        BlogPostContent content = blogService.addBlogPostContent(
                blogId,
                request.getText(),
                request.getImageUrl()
        );
        return ResponseEntity.ok(content);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Blog>> getAll() {
        return blogService.getAll();
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveBlog(@RequestBody BlogRequest blog) {
        return blogService.addBlog(blog);
    }
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping(path = "/author", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBlogAuthor(@ModelAttribute AuthorRequest authorRequest) {
        try {
            Author savedAuthor = blogService.createAuthor(authorRequest);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (
                IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping("/blog-author")
    public ResponseEntity<List<Author>> getBlogAuthors(){
        return blogService.getAllAuthor();
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @GetMapping("/author/{name}")
    public ResponseEntity<AuthorDTO>  getAuthor(@PathVariable("name") String name){

        return ResponseEntity.ok(blogService.getAuthorByName(name));
    }
}
