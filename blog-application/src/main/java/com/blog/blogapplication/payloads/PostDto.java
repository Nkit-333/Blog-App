package com.blog.blogapplication.payloads;

import com.blog.blogapplication.model.Category;
import com.blog.blogapplication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
public class PostDto {

    private  Integer postId;

    private  String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;
}
