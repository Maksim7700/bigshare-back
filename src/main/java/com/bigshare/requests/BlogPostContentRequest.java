package com.bigshare.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostContentRequest {

    private String title;
    private String text;
    private String image;
    private String fileName;
    private String type;
}
