package com.dev.shop.services.category;

import com.dev.shop.domain.entities.Category;
import com.dev.shop.dtos.requests.CategoryCreateRequest;
import com.dev.shop.dtos.requests.CategoryUpdateRequest;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryCreateRequest request);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    Category updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategoryById(Long id);
}
