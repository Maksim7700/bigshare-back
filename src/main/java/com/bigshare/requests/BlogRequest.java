package com.bigshare.requests;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BlogRequest {

    private String title;
    private String content;
    private Long authorId;
    private MultipartFile image;
}
