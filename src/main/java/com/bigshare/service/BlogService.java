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
import java.util.Base64;
import java.util.Optional;

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
    public ResponseEntity<?> addBlog(BlogRequest blogRequest) {
        Author author = authorRepository.findById(blogRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + blogRequest.getAuthorId()));

        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setAuthor(author);

        // Якщо зображення передано в Base64, зберігаємо його
        if (isPresentImage(blogRequest)) {
            byte[] imageBytes = Base64.getDecoder().decode(blogRequest.getImage()); // Перетворюємо Base64 на байти

            BlogImage image = new BlogImage();
            image.setName(blogRequest.getFileName()); // Використовуємо передану назву файлу
            image.setType(blogRequest.getType()); // Використовуємо переданий тип зображення
            image.setData(imageBytes); // Зберігаємо байти зображення

            image = blogImageRepository.save(image);
            blog.setImage(image);
        }

        blogRepository.save(blog);

        return ResponseEntity.status(HttpStatus.CREATED).body("Blog added successfully.");
    }


    @Transactional
    public void addBlogPostContent(long blogId, BlogPostContentRequest request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + blogId));

        BlogPostContent blogPostContent = new BlogPostContent();
        blogPostContent.setText(request.getText());
        blogPostContent.setTitle(request.getTitle());
        blogPostContent.setBlog(blog);

        if (isPresentImage(request)) {
            byte[] decodedImage = Base64.getDecoder().decode(request.getImage());

            BlogImage image = new BlogImage();
            image.setName(request.getFileName());
            image.setType(request.getType());
            image.setData(decodedImage);

            image = blogImageRepository.save(image);
            blogPostContent.setImage(image);
        }

        blogPostContentRepository.save(blogPostContent);
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
