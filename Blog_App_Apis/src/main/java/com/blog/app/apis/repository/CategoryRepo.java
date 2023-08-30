package com.blog.app.apis.repository;

import com.blog.app.apis.entityes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer > {
}
