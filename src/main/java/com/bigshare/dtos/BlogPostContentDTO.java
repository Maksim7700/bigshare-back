package com.bigshare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostContentDTO {
    private Long id;
    private String title;
    private String text;
    private BlogImageDTO image;
}
