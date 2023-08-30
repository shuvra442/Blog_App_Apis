package com.blog.app.apis.services;

import com.blog.app.apis.dto.CategoryDto;
import com.blog.app.apis.dto.PostDto;
import com.blog.app.apis.dto.responseDto.PostResponce;
import com.blog.app.apis.entityes.Post;

import java.util.List;

public interface PostService {

    PostDto getPostById(Integer postId);

    PostResponce getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);


    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void delete(Integer postId);

    // Get all post by category
    List<PostDto>getAllPostByCategory(Integer categoryId);

    // Get All Post By User

    List<PostDto>GetAllPostByUser(Integer userId);

    List<PostDto> searchPost(String keyword);
}
