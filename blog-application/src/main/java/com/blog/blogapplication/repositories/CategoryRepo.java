package com.blog.blogapplication.repositories;

import com.blog.blogapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
