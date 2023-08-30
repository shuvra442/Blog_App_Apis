package com.blog.app.apis.controller;

import com.blog.app.apis.dto.CommentDto;
import com.blog.app.apis.dto.responseDto.ApiResponses;
import com.blog.app.apis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){
        CommentDto create=this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(create, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity(new ApiResponses("Comment Successfully Deleted",true),HttpStatus.OK);
    }

}
