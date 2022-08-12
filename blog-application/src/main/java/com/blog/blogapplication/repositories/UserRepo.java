package com.blog.blogapplication.repositories;

import com.blog.blogapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

    Object findAllById(Integer userId);
}
