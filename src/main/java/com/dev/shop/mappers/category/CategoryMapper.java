package com.dev.shop.mappers.category;

import com.dev.shop.constants.ErrorMessage;
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

    private final CategoryRepository categoryRepository;

    @Override
    public Category toCategory(CategoryCreateRequest request) {
        String name = request.getName();

        existedByName(name);

        return Category.builder().name(name).build();
    }

    @Override
    public Category updateCategory(Category category, CategoryUpdateRequest request) {
        String name = request.getName();

        existedByName(name);

        category.setName(name);

        return category;
    }

    private void existedByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new AlreadyExistsException(name + ErrorMessage.ALREADY_EXISTS);
        }
    }
}
