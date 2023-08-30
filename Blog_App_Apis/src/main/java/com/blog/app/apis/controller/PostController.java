package com.blog.app.apis.controller;

import com.blog.app.apis.config.AppConstant;
import com.blog.app.apis.dto.PostDto;
import com.blog.app.apis.dto.responseDto.ApiResponses;
import com.blog.app.apis.dto.responseDto.PostResponce;
import com.blog.app.apis.services.FileService;
import com.blog.app.apis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @GetMapping("/posts")
    public ResponseEntity<PostResponce>getAllPost(
            @RequestParam(value ="pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
            @RequestParam (value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
        PostResponce postResponce=this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponce>(postResponce,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable  Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody  PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    @PutMapping("/put/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto update=this.postService.updatePost(postDto, postId);
        return new ResponseEntity(update,HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable Integer postId){
        this.postService.delete(postId);
        return new ResponseEntity(new ApiResponses("Category Deleted succesfully",true),HttpStatus.OK);
    }


    // Get User By id
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postDtos=this.postService.GetAllPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    // Get Category BY id
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos=this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    // Search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity< List<PostDto> > searchTitle(@PathVariable("keyword") String keyword){
        List<PostDto> postDto1=this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto> >(postDto1,HttpStatus.OK);
    }

    // Post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public  ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                          @PathVariable Integer postId ) throws IOException {
        PostDto postDto= this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost= this.postService.updatePost(postDto,postId);
      return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws  IOException{

        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
