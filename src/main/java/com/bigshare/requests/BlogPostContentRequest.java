package com.bigshare.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostContentRequest {

    private String title;
    private String text;
    private MultipartFile image;
}
