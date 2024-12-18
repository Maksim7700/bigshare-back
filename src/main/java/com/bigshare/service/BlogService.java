package com.bigshare.service;

import com.bigshare.converters.BlogConverter;
import com.bigshare.converters.BlogPostContentConverter;
import com.bigshare.dtos.BlogDTO;
import com.bigshare.dtos.BlogPostContentDTO;
import com.bigshare.dtos.BlogViewDTO;
import com.bigshare.model.author.Author;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;

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
    public ResponseEntity<?> addBlogPostContent(long blogId, BlogPostContentRequest blogPostContentRequest) throws IOException {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + blogId));

        BlogPostContent blogPostContent = new BlogPostContent();
        blogPostContent.setText(blogPostContentRequest.getText());
        blogPostContent.setTitle(blogPostContentRequest.getTitle());
        blogPostContent.setBlog(blog);

        if (isPresentImage(blogPostContentRequest)) {
            BlogImage image = new BlogImage();
            image.setName(blogPostContentRequest.getImage().getOriginalFilename());
            image.setType(blogPostContentRequest.getImage().getContentType());
            image.setData(blogPostContentRequest.getImage().getBytes());

            image = blogImageRepository.save(image);
            blogPostContent.setImage(image);
        }
        blogPostContentRepository.save(blogPostContent);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Page<BlogPostContentDTO>> getAllBlogPostContents(long blogId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPostContentDTO> blogPostContents = blogPostContentRepository
                .findByBlogId(blogId, pageable)
                .map(BlogPostContentConverter::toBlogPostContentDTO);
        return ResponseEntity.ok(blogPostContents);
    }

    @Transactional
    public ResponseEntity<Page<BlogDTO>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<BlogDTO> blogs = blogRepository.findAll(pageable).map(
                BlogConverter::toBlogDto
        );

        return ResponseEntity.ok(blogs);
    }



    @Transactional
    public ResponseEntity<Page<BlogDTO>> getAllPosted(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<BlogDTO> blogs = blogRepository.findAllByPostedIsTrue(pageable).map(
                BlogConverter::toBlogDto
        );
        return ResponseEntity.ok(blogs);
    }

    @Transactional
    public ResponseEntity<BlogViewDTO> getBlogView(long blogId) {
        return ResponseEntity.ok(BlogConverter.toBlogViewDto(blogRepository.getById(blogId)));
    }

    @Transactional
    public ResponseEntity<?> updateBlog(long blogId, boolean status) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found with id: " + blogId));
        blog.setPosted(status);
        blogRepository.save(blog);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteBlog(long blogId) {
        blogRepository.deleteById(blogId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBlogPostContent(long blogPostContentId) {
        blogPostContentRepository.deleteById(blogPostContentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<Page<BlogDTO>> getLatestBlogsExcluding(Long excludeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> latestBlogs = blogRepository.findByIdNotOrderByCreatedAtDesc(excludeId, pageable);
        Page<BlogDTO> blogDTOs = latestBlogs.map(BlogConverter::toBlogDto);
        return ResponseEntity.ok(blogDTOs);
    }

    private boolean isPresentImage(BlogPostContentRequest request) {
        return request.getImage() != null && !request.getImage().isEmpty();
    }

    private boolean isPresentImage(BlogRequest request) {
        return request.getImage() != null && !request.getImage().isEmpty();
    }
}
