package com.romel.blogapp.repository;

import com.romel.blogapp.mainStuff.PostBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBlogRepository extends JpaRepository<PostBlog, Long> {
}
