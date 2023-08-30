package com.blog.app.apis.controller;

import com.blog.app.apis.dto.UserDto;
import com.blog.app.apis.dto.requestDto.JwtAuthRequest;
import com.blog.app.apis.dto.responseDto.JwtAuthResponce;
import com.blog.app.apis.exception.ApiException;
import com.blog.app.apis.security.JwtTokenHelpper;
import com.blog.app.apis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelpper jwtTokenHelpper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponce> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails= this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token= this.jwtTokenHelpper.generateToken(userDetails);
        JwtAuthResponce responce=new JwtAuthResponce();
        responce.setToken(token);
        return new ResponseEntity<JwtAuthResponce>(responce, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username,password);

        try {

        this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details !! ");
            throw new ApiException("Invalid username or password");
        }
    }

    // register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
      UserDto  registeredUser= this.userService.registerNewUser(userDto);
      return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }

}
