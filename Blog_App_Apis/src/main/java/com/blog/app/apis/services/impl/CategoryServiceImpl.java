package com.blog.app.apis.services.impl;

import com.blog.app.apis.dto.CategoryDto;
import com.blog.app.apis.entityes.Category;
import com.blog.app.apis.exception.ResourceNotFoundException;
import com.blog.app.apis.repository.CategoryRepo;
import com.blog.app.apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        return this.categoryToDto(category);
    }


    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category>categories=this.categoryRepo.findAll();
        List<CategoryDto>categoryDtos=categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.dtoToCategory(categoryDto);
        Category saveCategory=this.categoryRepo.save(category);
        return this.categoryToDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescriptions(categoryDto.getCategoryDescriptions());
        Category updateCategory=this.categoryRepo.save(category);
        CategoryDto categoryDto1=this.categoryToDto(updateCategory);
        return categoryDto1;
    }

    @Override
    public void delete(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        this.categoryRepo.delete(category);
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category=this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto=this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
