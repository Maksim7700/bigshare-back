package com.bigshare.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AuthorRequest {
    private String name;
    private MultipartFile image;
}
