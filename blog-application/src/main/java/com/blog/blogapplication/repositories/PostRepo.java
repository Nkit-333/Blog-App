package com.blog.blogapplication.repositories;

import com.blog.blogapplication.model.Category;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
//    @Query("select p from post p where p.post_title like :key")
//    List<Post> searchByTitle(@Param("key") String title);
}
