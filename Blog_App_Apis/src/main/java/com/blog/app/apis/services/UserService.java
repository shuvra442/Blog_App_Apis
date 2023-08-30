package com.blog.app.apis.services;


import com.blog.app.apis.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    List<UserDto> getAllUser();
    UserDto getUserById(Integer userId);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);

    void deleteUser(Integer userId);







}
