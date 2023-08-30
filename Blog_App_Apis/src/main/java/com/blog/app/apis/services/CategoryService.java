package com.blog.app.apis.services;

import com.blog.app.apis.dto.CategoryDto;
import org.springframework.data.annotation.CreatedBy;

import javax.naming.InsufficientResourcesException;
import java.util.List;

public interface CategoryService {

    CategoryDto getCategoryById(Integer categoryId);

    List<CategoryDto> getAllCategory();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    void delete(Integer categoryId);

}
