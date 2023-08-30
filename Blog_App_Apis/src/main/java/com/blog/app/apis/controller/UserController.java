package com.blog.app.apis.controller;

import com.blog.app.apis.dto.responseDto.ApiResponses;
import com.blog.app.apis.dto.UserDto;
import com.blog.app.apis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userServices;

    //Get -> get all users
    @GetMapping("/")
    public ResponseEntity< List<UserDto> > getAllUser(){
        return ResponseEntity.ok(this.userServices.getAllUser());
    }

    //Get -> get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(this.userServices.getUserById(userId));
    }

    // post -> create user
    @PostMapping("/post")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto){
       UserDto createUserDto= this.userServices.createUser(userDto);
       return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    // put -> update user
    @PutMapping("/{userId}")
    public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
       UserDto update=this.userServices.updateUser(userDto, userId);
       return ResponseEntity.ok(update);
    }


    // delete -> delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponses> deleteUser(@PathVariable("userId") Integer userId){
     this.userServices.deleteUser(userId);
     return new ResponseEntity(new ApiResponses("User Deleted SuccessFully", true),HttpStatus.OK);
    }


}

