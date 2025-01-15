package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.requests.CategoryCreateRequest;
import com.dev.shop.domain.dtos.requests.CategoryUpdateRequest;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.CategoryResponse;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.mappers.category.ICategoryMapper;
import com.dev.shop.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    private final ICategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(
                ApiResponse.<List<CategoryResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get all categories success.")
                        .data(categoryService.getAllCategories()
                                .stream()
                                .map(categoryMapper::toCategoryResponse)
                                .toList()
                        )
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryCreateRequest request) {
        Category savedCategory = categoryService.createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<CategoryResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .message("Create category success.")
                                .data(categoryMapper.toCategoryResponse(savedCategory))
                                .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get category success.")
                        .data(categoryMapper.toCategoryResponse(category))
                        .build()
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByName(@PathVariable("name") String name) {
        Category category = categoryService.getCategoryByName(name);

        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get category success.")
                        .data(categoryMapper.toCategoryResponse(category))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete category success.")
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody CategoryUpdateRequest request
    ) {
        Category updatedCategory = categoryService.updateCategory(id, request);

        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Update category success.")
                        .data(categoryMapper.toCategoryResponse(updatedCategory))
                        .build()
        );
    }
}
