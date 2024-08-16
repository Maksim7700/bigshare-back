package com.bigshare.service;

import com.bigshare.model.blog.Author;
import com.bigshare.model.blog.Blog;
import com.bigshare.model.blog.BlogPostContent;
import com.bigshare.model.blog.Image;
import com.bigshare.model.requests.AuthorRequest;
import com.bigshare.model.requests.BlogRequest;
import com.bigshare.model.responses.AuthorDTO;
import com.bigshare.model.responses.ImageDTO;
import com.bigshare.repository.AuthorRepository;
import com.bigshare.repository.BlogPostContentRepository;
import com.bigshare.repository.BlogRepository;
import com.bigshare.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogPostContentRepository blogPostContentRepository;
    private final AuthorRepository authorRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, BlogPostContentRepository blogPostContentRepository, AuthorRepository authorRepository, ImageRepository imageRepository) {
        this.blogRepository = blogRepository;
        this.blogPostContentRepository = blogPostContentRepository;
        this.authorRepository = authorRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ResponseEntity<?> addBlog(BlogRequest blogRequest) {
        Author author = authorRepository.findById(blogRequest.getAuthorId())
            .orElseThrow(() -> new RuntimeException("Author not found with id: " + blogRequest.getAuthorId()));

        Blog blog = new Blog();
        blog.setTitle(blog.getTitle());
        blog.setContent(blog.getContent());
        blog.setAuthor(author);

        return ResponseEntity.ok(blogRepository.save(blog));
    }

    @Transactional
    public BlogPostContent addBlogPostContent(Long blogId, String text, String imageUrl) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + blogId));

        BlogPostContent blogPostContent = new BlogPostContent();
        blogPostContent.setText(text);
        blogPostContent.setImageUrl(imageUrl);
        blogPostContent.setBlog(blog);

        return blogPostContentRepository.save(blogPostContent);
    }

    @Transactional
    public ResponseEntity<List<Blog>> getAll() {
        return ResponseEntity.ok(blogRepository.findAll());
    }



    public ResponseEntity<List<Author>> getAllAuthor() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    public Author createAuthor(AuthorRequest authorRequest) throws IOException {
        Author author = new Author();
        author.setName(authorRequest.getName());

        if (authorRequest.getImage() != null && !authorRequest.getImage().isEmpty()) {
            Image image = new Image();
            image.setName(authorRequest.getImage().getOriginalFilename());
            image.setType(authorRequest.getImage().getContentType());
            image.setData(authorRequest.getImage().getBytes());

            image = imageRepository.save(image);
            author.setImage(image);
        }

        return authorRepository.save(author);
    }

    public AuthorDTO getAuthorByName(String name) {
        Author author = authorRepository.findByName(name);
        if (author != null) {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(author.getId());
            authorDTO.setName(author.getName());

            Image image = author.getImage();
            if (image != null) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(image.getId());
                imageDTO.setType(image.getType());
                imageDTO.setName(image.getName());

                // Convert byte[] to Base64-encoded string
                String base64Image = Base64.getEncoder().encodeToString(image.getData());
                imageDTO.setData(base64Image);

                authorDTO.setImage(imageDTO);
            }

            return authorDTO;
        } else {
            return null; // Handle not found scenario
        }
    }

}
