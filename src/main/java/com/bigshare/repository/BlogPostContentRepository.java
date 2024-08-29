package com.bigshare.repository;

import com.bigshare.model.blog.BlogPostContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostContentRepository extends JpaRepository<BlogPostContent, Long> {

    Page<BlogPostContent> findByBlogId(Long blogId, Pageable pageable);

}
