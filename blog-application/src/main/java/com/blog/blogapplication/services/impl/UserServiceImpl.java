package com.blog.blogapplication.services.impl;

import com.blog.blogapplication.exceptions.ResourceNotFoundException;
import com.blog.blogapplication.model.User;
import com.blog.blogapplication.payloads.UserDto;
import com.blog.blogapplication.repositories.UserRepo;
import com.blog.blogapplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser=this.userRepo.save(user);
        return this.userTODto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=this.userRepo.save(user);
        return this.userTODto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user","id",userId));

        return this.userTODto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= this.userRepo.findAll();
        List<UserDto> userDto = users.stream().map(user -> this.userTODto(user)).collect(Collectors.toList());

        return userDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        this.userRepo.deleteById(userId);
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }
    public UserDto userTODto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
