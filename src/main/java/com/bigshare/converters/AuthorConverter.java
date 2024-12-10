package com.bigshare.converters;

import com.bigshare.model.author.Author;
import com.bigshare.model.author.AuthorImage;
import com.bigshare.dtos.AuthorDTO;
import com.bigshare.dtos.AuthorImageDTO;

import java.util.Base64;

public class AuthorConverter {

    public static AuthorDTO convertToDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());

        AuthorImage image = author.getImage();
        if (image != null) {
            AuthorImageDTO imageDTO = new AuthorImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setType(image.getType());
            imageDTO.setName(image.getName());

            String base64Image = Base64.getEncoder().encodeToString(image.getData());
            imageDTO.setData(base64Image);

            authorDTO.setImage(imageDTO);
        }

        return authorDTO;
    }
}
