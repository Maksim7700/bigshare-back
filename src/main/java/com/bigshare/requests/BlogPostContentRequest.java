package com.bigshare.requests;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BlogPostContentRequest {

    private String title;
    private String text;
    private MultipartFile image;
}
