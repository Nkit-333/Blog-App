package com.blog.blogapplication.controllers;

import com.blog.blogapplication.payloads.ApiResponse;
import com.blog.blogapplication.payloads.CategoryDto;
import com.blog.blogapplication.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> createCategory(@Valid @PathVariable Integer catId,@RequestBody CategoryDto categoryDto){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK );
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted",true),
                HttpStatus.OK);
    }
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto category= this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(category,HttpStatus.FOUND);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtoList = this.categoryService.getCategories();
        return new ResponseEntity<List<CategoryDto>>(categoryDtoList,HttpStatus.OK);
    }

}
