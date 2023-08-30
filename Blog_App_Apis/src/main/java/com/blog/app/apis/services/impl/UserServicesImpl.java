package com.blog.app.apis.services.impl;

import com.blog.app.apis.config.AppConstant;
import com.blog.app.apis.dto.UserDto;
import com.blog.app.apis.entityes.Role;
import com.blog.app.apis.entityes.User;
import com.blog.app.apis.exception.ResourceNotFoundException;
import com.blog.app.apis.repository.RoleRepo;
import com.blog.app.apis.repository.UserRepo;
import com.blog.app.apis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
       User user= this.modelMapper.map(userDto, User.class);
       //encoded password
       user.setPassword(this.passwordEncoder.encode(user.getPassword()));
       //Roles user
       Role role=this.roleRepo.findById(AppConstant.NORMAL_USER).get();
       user.getRole().add(role);
       User newUser=this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
       List<User> users=this.userRepo.findAll();
       List<UserDto>userDtos= users.stream()
                                   .map(user->this.userToDto(user))
                                   .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User updatedUser= this.userRepo.save(user);
        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto, User.class);

        /**
         *         user.setId(userDto.getId());
         *         user.setName(userDto.getName());
         *         user.setPassword(userDto.getPassword());
         *         user.setEmail(userDto.getEmail());
         *         user.setAbout(userDto.getAbout());
         */
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user, UserDto.class);
        /**
         *         userDto.setId(user.getId());
         *         userDto.setName(user.getName());
         *         userDto.setEmail(user.getEmail());
         *         userDto.setPassword(user.getPassword());
         *         userDto.setAbout(user.getAbout());
         */
        return userDto;
    }

}
