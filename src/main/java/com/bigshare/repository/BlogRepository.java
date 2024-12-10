package com.bigshare.repository;

import com.bigshare.model.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAllByPostedIsTrue(Pageable pageable);
    Page<Blog> findByIdNotOrderByCreatedAtDesc(Long excludeId, Pageable pageable);
}
