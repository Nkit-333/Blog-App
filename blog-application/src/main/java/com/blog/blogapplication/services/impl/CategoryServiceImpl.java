package com.blog.blogapplication.services.impl;

import com.blog.blogapplication.exceptions.ResourceNotFoundException;
import com.blog.blogapplication.model.Category;
import com.blog.blogapplication.payloads.CategoryDto;
import com.blog.blogapplication.repositories.CategoryRepo;
import com.blog.blogapplication.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category= this.dtoToCategory(categoryDto);
        Category savedCategory= this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        this.categoryRepo.save(category);
        return this.categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        this.categoryRepo.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categoryList = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtoList=categoryList.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtoList;
    }
    private CategoryDto categoryToDto(Category category){
      return  this.modelMapper.map(category,CategoryDto.class);
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto,Category.class);
    }
}
