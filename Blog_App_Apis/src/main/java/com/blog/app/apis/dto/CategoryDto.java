package com.blog.app.apis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int id;
    @NotBlank
    private String categoryTitle;
    @NotBlank
    @Size(min = 10, max = 100)
    private String categoryDescriptions;
}
