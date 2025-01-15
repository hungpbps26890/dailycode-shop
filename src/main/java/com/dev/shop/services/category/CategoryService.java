package com.dev.shop.services.category;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.dtos.requests.CategoryCreateRequest;
import com.dev.shop.dtos.requests.CategoryUpdateRequest;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.category.ICategoryMapper;
import com.dev.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final ICategoryMapper categoryMapper;

    @Override
    public Category createCategory(CategoryCreateRequest request) {
        return categoryRepository.save(categoryMapper.toCategory(request));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category updateCategory(Long id, CategoryUpdateRequest request) {
        return categoryRepository.findById(id)
                .map(existingCategory -> categoryMapper.updateCategory(existingCategory, request))
                .map(categoryRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND);
                });
    }
}
