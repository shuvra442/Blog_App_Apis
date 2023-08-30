package com.blog.app.apis.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 2, message = "Name must be of 2 char")
    private String name;
    @Email(message = "Enter valid email")
    private String email;
    @NotEmpty
    @Size(min = 4, max = 10, message = "Password must be of 4 and max of 10 char")
    private String password;
    @NotEmpty
     @Size(max = 100, message = "About required")
    private String about;

    private Set<RoleDto> role =new HashSet<>();
}
