package com.blog.app.apis.controller;

import com.blog.app.apis.dto.CategoryDto;
import com.blog.app.apis.dto.responseDto.ApiResponses;
import com.blog.app.apis.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId ){
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>>getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }

    @PostMapping("/post")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto create=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(create,HttpStatus.CREATED);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId){
        CategoryDto update=this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity(update,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        this.categoryService.delete(categoryId);
        return new ResponseEntity(new ApiResponses("Category Deleted succesfully",true),HttpStatus.OK);
    }

}