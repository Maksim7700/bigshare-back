package com.bigshare.controller;


import com.bigshare.dtos.BlogDTO;
import com.bigshare.dtos.BlogViewDTO;
import com.bigshare.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/view/blogs")
@AllArgsConstructor
public class BlogViewController {

    private BlogService blogService;


    @GetMapping
    public ResponseEntity<Page<BlogDTO>> getBlogs(
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAllPosted(size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogViewDTO> getBlogView(@PathVariable Long id) {
        return blogService.getBlogView(id);
    }
}
