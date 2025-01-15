package com.dev.shop.mappers.category;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.CategoryResponse;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.domain.dtos.requests.CategoryCreateRequest;
import com.dev.shop.domain.dtos.requests.CategoryUpdateRequest;
import com.dev.shop.exceptions.AlreadyExistsException;
import com.dev.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements ICategoryMapper {

    @Override
    public Category toCategory(CategoryCreateRequest request) {
        String name = request.getName();

        return Category.builder().name(name).build();
    }

    @Override
    public Category updateCategory(Category category, CategoryUpdateRequest request) {
        String name = request.getName();

        category.setName(name);

        return category;
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
