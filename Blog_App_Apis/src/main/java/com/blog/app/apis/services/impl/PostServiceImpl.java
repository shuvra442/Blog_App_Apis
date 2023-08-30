package com.blog.app.apis.services.impl;

import com.blog.app.apis.dto.PostDto;
import com.blog.app.apis.dto.responseDto.PostResponce;
import com.blog.app.apis.entityes.Category;
import com.blog.app.apis.entityes.Post;
import com.blog.app.apis.entityes.User;
import com.blog.app.apis.exception.ResourceNotFoundException;
import com.blog.app.apis.repository.CategoryRepo;
import com.blog.app.apis.repository.PostRepo;
import com.blog.app.apis.repository.UserRepo;
import com.blog.app.apis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto getPostById(Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
       return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponce getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost=this.postRepo.findAll(pageable);
        List<Post>allPost=pagePost.getContent();
        List<PostDto>postDtos=allPost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponce postResponce=new PostResponce();
        postResponce.setContent(postDtos);
        postResponce.setPageNumber(pagePost.getNumber());
        postResponce.setPageSize(pagePost.getSize());
        postResponce.setTotalElements(pagePost.getTotalElements());
        postResponce.setTotalPage(pagePost.getTotalPages());
        postResponce.setLastPage(pagePost.isLast());
        return postResponce;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        Post post=this.modelMapper.map(postDto, Post.class);
        post.setImageName("Default PNG");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savePost=this.postRepo.save(post);
        return this.modelMapper.map(savePost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post savePost=this.postRepo.save(post);
        return this.modelMapper.map(savePost,PostDto.class);
    }

    @Override
    public void delete(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        List<Post>posts=this.postRepo.findByCategory(category);
        List<PostDto>postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> GetAllPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        List<Post>posts=this.postRepo.findByUser(user);
        List<PostDto>postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
         List<Post> posts=this.postRepo.findByTitleContaining(keyword);
         List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
