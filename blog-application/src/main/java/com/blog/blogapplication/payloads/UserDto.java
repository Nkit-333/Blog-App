package com.blog.blogapplication.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDto {
    private int id;
    //@Pattern can be used for regex

    @NotEmpty
    @Size(min = 4,message = "User name must be of 4 characters")
    private String name;
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty(message = "Password cannot be null" )
    @Size(min = 3)
    private String password;
    @NotNull(message = "about cannot be null")
    private String about;

}
