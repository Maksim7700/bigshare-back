package com.bigshare.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

    private String title;
    private String content;
    private Long authorId;
    private MultipartFile image;
}
