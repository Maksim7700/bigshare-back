package com.bigshare.converters;

import com.bigshare.dtos.BlogDTO;
import com.bigshare.dtos.BlogImageDTO;
import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogImage;

public class BlogConverter {

        public static BlogDTO toDto(Blog blog) {
            return new BlogDTO(
                    blog.getId(),
                    blog.getTitle(),
                    blog.getContent(),
                    AuthorConverter.convertToDTO(blog.getAuthor()),
                    blog.getCreatedAt(),
                    blog.getUpdatedAt(),
                    blog.isPosted(),
                    blog.getImage() != null ? BlogConverter.toBlogImageDTO(blog.getImage()) : null
            );
        }

        public static BlogImageDTO toBlogImageDTO(BlogImage blogImage) {
            return new BlogImageDTO(blogImage.getId(), blogImage.getData(), blogImage.getType(), blogImage.getName());
        }
}
