package com.bigshare.repository;

import com.bigshare.model.blog.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogImageRepository extends JpaRepository<BlogImage, Long> {
}
