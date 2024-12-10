package com.bigshare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogViewDTO {
    private Long id;
    private String title;
    private String content;
    private AuthorDTO author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean posted;
    private List<BlogPostContentDTO> blogPostContentDTOList;
    private BlogImageDTO imageUrl;
}
