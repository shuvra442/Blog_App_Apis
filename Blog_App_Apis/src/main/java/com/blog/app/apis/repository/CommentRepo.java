package com.blog.app.apis.repository;

import com.blog.app.apis.entityes.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
