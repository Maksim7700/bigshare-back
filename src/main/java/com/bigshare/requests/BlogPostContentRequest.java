package com.bigshare.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostContentRequest {

    private String title;
    private String text;
    private MultipartFile image;
}
