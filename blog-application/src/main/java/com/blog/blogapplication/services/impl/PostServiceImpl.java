package com.blog.blogapplication.services.impl;

import com.blog.blogapplication.exceptions.ResourceNotFoundException;
import com.blog.blogapplication.model.Category;
import com.blog.blogapplication.model.Post;
import com.blog.blogapplication.model.User;
import com.blog.blogapplication.payloads.PostDto;
import com.blog.blogapplication.payloads.PostResponse;
import com.blog.blogapplication.repositories.CategoryRepo;
import com.blog.blogapplication.repositories.PostRepo;
import com.blog.blogapplication.repositories.UserRepo;
import com.blog.blogapplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        Category category = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost =this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepo.deleteById(postId);
    }

    @Override
    public PostResponse getAllPost(Integer PageNumber, Integer PageSize,String sortBy) {
        Pageable pageable = PageRequest.of(PageNumber,PageSize, Sort.by(sortBy).descending());
        Page<Post> postPage =this.postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse =  new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageSize(postPage.getSize());
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUSer(Integer userId) {
        User user = this.userRepo.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String Keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(Keyword);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }
}
