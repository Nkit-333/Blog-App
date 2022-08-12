package com.blog.blogapplication.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min=4)
    private String categoryTitle;
    @NotBlank
    @Size(min=10)
    private String categoryDescription;

}
