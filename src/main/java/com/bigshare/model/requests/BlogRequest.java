package com.bigshare.model.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

    private String title;
    private String content;
    private Long authorId;
}
