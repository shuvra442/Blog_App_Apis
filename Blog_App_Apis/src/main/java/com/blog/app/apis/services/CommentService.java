package com.blog.app.apis.services;

import com.blog.app.apis.dto.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);
}
