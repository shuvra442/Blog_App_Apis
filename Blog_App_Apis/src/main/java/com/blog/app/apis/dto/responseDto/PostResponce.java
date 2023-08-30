package com.blog.app.apis.dto.responseDto;

import com.blog.app.apis.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponce{

    private List<PostDto>content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPage;
    private boolean lastPage;
}
