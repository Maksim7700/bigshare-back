package com.bigshare.converters;

import com.bigshare.dtos.BlogPostContentDTO;
import com.bigshare.model.blog.BlogPostContent;

public class BlogPostContentConverter {

    public static BlogPostContentDTO toBlogPostContentDTO(BlogPostContent blogPostContent) {
        BlogPostContentDTO blogPostContentDTO = new BlogPostContentDTO();
        blogPostContentDTO.setId(blogPostContent.getId());
        blogPostContentDTO.setTitle(blogPostContent.getTitle());
        blogPostContentDTO.setText(blogPostContent.getText());
        blogPostContentDTO.setImage(blogPostContent.getImage() != null ? BlogConverter.toBlogImageDTO(blogPostContent.getImage()) : null);
        return blogPostContentDTO;
    }
}
