package com.blog.app.apis.repository;

import com.blog.app.apis.entityes.Category;
import com.blog.app.apis.entityes.Post;
import com.blog.app.apis.entityes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);

    List<Post>findByTitleContaining(String title);
}
