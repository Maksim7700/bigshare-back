package com.bigshare.model.author;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    private String type;
    private String name;
}
