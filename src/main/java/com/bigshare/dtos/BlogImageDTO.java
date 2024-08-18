package com.bigshare.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogImageDTO {
    private Long id;
    private byte[] data;
    private String type;
    private String name;

    // Constructors, getters, and setters
}
