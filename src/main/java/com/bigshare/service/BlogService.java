package com.bigshare.service;

import com.bigshare.converters.BlogConverter;
import com.bigshare.dtos.BlogDTO;
import com.bigshare.model.author.Author;
import com.bigshare.model.author.AuthorImage;
import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogImage;
import com.bigshare.model.blog.BlogPostContent;
import com.bigshare.repository.AuthorRepository;
import com.bigshare.repository.BlogImageRepository;
import com.bigshare.repository.BlogPostContentRepository;
import com.bigshare.repository.BlogRepository;
import com.bigshare.requests.BlogPostContentRequest;
import com.bigshare.requests.BlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogPostContentRepository blogPostContentRepository;
    private final AuthorRepository authorRepository;
    private final BlogImageRepository blogImageRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, BlogPostContentRepository blogPostContentRepository, AuthorRepository authorRepository, BlogImageRepository blogImageRepository) {
        this.blogRepository = blogRepository;
        this.blogPostContentRepository = blogPostContentRepository;
        this.authorRepository = authorRepository;
        this.blogImageRepository = blogImageRepository;
    }

    @Transactional
    public ResponseEntity<?> addBlog(BlogRequest blogRequest) throws IOException {
        Author author = authorRepository.findById(blogRequest.getAuthorId())
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + blogRequest.getAuthorId()));

        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setAuthor(author);
        if (isPresentImage(blogRequest)) {
            BlogImage image = new BlogImage();
            image.setName(blogRequest.getImage().getOriginalFilename());
            image.setType(blogRequest.getImage().getContentType());
            image.setData(blogRequest.getImage().getBytes());

            image = blogImageRepository.save(image);
            blog.setImage(image);
        }

        blogRepository.save(blog);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<BlogPostContent> addBlogPostContent(Long blogId, BlogPostContentRequest blogPostContentRequest) throws IOException {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + blogId));

        BlogPostContent blogPostContent = new BlogPostContent();
        blogPostContent.setText(blogPostContentRequest.getText());
        blogPostContent.setBlog(blog);

        if (isPresentImage(blogPostContentRequest)) {
            BlogImage image = new BlogImage();
            image.setName(blogPostContentRequest.getImage().getOriginalFilename());
            image.setType(blogPostContentRequest.getImage().getContentType());
            image.setData(blogPostContentRequest.getImage().getBytes());

            image = blogImageRepository.save(image);
            blogPostContent.setImage(image);
        }

        return ResponseEntity.ok(blogPostContentRepository.save(blogPostContent));
    }

    @Transactional
    public ResponseEntity<Page<BlogDTO>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogDTO> blogs = blogRepository.findAll(pageable).map(
                BlogConverter::toDto
        );
        return ResponseEntity.ok(blogs);
    }


    private boolean isPresentImage(BlogPostContentRequest request) {
        return request.getImage() != null && !request.getImage().isEmpty();
    }

    private boolean isPresentImage(BlogRequest request) {
        return request.getImage() != null && !request.getImage().isEmpty();
    }
}
