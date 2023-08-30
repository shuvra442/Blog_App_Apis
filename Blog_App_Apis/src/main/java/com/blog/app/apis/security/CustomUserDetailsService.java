package com.blog.app.apis.security;

import com.blog.app.apis.entityes.User;
import com.blog.app.apis.exception.ResourceNotFoundException;
import com.blog.app.apis.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user","email"+username,0));
        return user;
    }
}
