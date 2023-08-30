package com.blog.app.apis.repository;

import com.blog.app.apis.entityes.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
