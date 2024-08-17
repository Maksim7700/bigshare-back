package com.bigshare.repository;

import com.bigshare.model.author.AuthorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorImageRepository extends JpaRepository<AuthorImage, Long> {
}
