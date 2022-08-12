package com.blog.blogapplication.controllers;

import com.blog.blogapplication.payloads.ApiResponse;
import com.blog.blogapplication.payloads.UserDto;
import com.blog.blogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto) {
     UserDto createUserDto = this.userService.createUser(userDto);
     return  new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Integer id,@RequestBody UserDto userDto){
        UserDto updatedUserDto = this.userService.updateUser(userDto,id);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }
    @GetMapping("/{id}/")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return  ResponseEntity.ok(this.userService.getAllUsers());
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted",true),HttpStatus.OK);
    }
}
