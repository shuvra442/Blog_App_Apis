package com.blog.app.apis.repository;

import com.blog.app.apis.entityes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User>findByEmail(String email);

}
