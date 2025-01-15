package com.dev.shop.mappers.category;

import com.dev.shop.domain.entities.Category;
import com.dev.shop.dtos.requests.CategoryCreateRequest;
import com.dev.shop.dtos.requests.CategoryUpdateRequest;

public interface ICategoryMapper {

    Category toCategory(CategoryCreateRequest request);

    Category updateCategory(Category category, CategoryUpdateRequest request);
}
