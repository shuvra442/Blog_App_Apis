package com.blog.app.apis.dto.requestDto;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
