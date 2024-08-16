package com.bigshare.repository;

import com.bigshare.model.blog.BlogPostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostContentRepository extends JpaRepository<BlogPostContent, Long> {
}
