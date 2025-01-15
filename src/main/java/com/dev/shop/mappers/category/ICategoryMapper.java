package com.dev.shop.mappers.category;

import com.dev.shop.domain.dtos.responses.CategoryResponse;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.domain.dtos.requests.CategoryCreateRequest;
import com.dev.shop.domain.dtos.requests.CategoryUpdateRequest;

public interface ICategoryMapper {

    Category toCategory(CategoryCreateRequest request);

    Category updateCategory(Category category, CategoryUpdateRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
