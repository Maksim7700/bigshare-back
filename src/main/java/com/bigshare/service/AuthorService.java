package com.bigshare.service;

import com.bigshare.converters.AuthorConverter;
import com.bigshare.dtos.AuthorDTO;
import com.bigshare.model.author.Author;
import com.bigshare.model.author.AuthorImage;
import com.bigshare.repository.AuthorRepository;
import com.bigshare.repository.AuthorImageRepository;
import com.bigshare.requests.AuthorRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorImageRepository authorImageRepository;
    private final AuthorRepository authorRepository;

    public Author createAuthor(AuthorRequest authorRequest) throws IOException {
        Author author = new Author();
        author.setName(authorRequest.getName());

        if (authorRequest.getImage() != null && !authorRequest.getImage().isEmpty()) {
            AuthorImage image = new AuthorImage();
            image.setName(authorRequest.getImage().getOriginalFilename());
            image.setType(authorRequest.getImage().getContentType());
            image.setData(authorRequest.getImage().getBytes());

            image = authorImageRepository.save(image);
            author.setImage(image);
        }

        return authorRepository.save(author);
    }

    public ResponseEntity<?> getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(AuthorConverter.convertToDTO(author.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author not found");
        }
    }

    public ResponseEntity<?> deleteById(Long id) {
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorRepository
                .findAll()
                .stream()
                .map(AuthorConverter::convertToDTO)
                .toList();

        return ResponseEntity.ok(authors);
    }
}
