package com.bigshare.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

    private String title;
    private String content;
    private Long authorId;
    private String image;
    private String fileName;
    private String type;
}
