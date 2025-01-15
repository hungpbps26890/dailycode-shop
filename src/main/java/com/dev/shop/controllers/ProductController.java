package com.dev.shop.controllers;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.requests.ProductCreateRequest;
import com.dev.shop.domain.dtos.requests.ProductUpdateRequest;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.ProductResponse;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.product.IProductMapper;
import com.dev.shop.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    private final IProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get all products success.")
                        .data(productService.getAllProducts()
                                .stream()
                                .map(productMapper::toProductResponse)
                                .toList()
                        )
                        .build()
        );
    }

    @GetMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get product success.")
                        .data(productMapper.toProductResponse(product))
                        .build()
        );
    }

    @GetMapping(name = "/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByName(@PathVariable("name") String name) {
        Product product = productService.getProductByName(name);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get product success.")
                        .data(productMapper.toProductResponse(product))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductCreateRequest request) {
        Product savedProduct = productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Create product success.")
                        .data(productMapper.toProductResponse(savedProduct))
                        .build()
                );
    }

    @PutMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductUpdateRequest request
    ) {
        Product updatedProduct = productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Update product success.")
                        .data(productMapper.toProductResponse(updatedProduct))
                        .build()
        );
    }

    @DeleteMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete product success.")
                        .build()
        );
    }

    @GetMapping(name = "/by-brand")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);

        if (products.isEmpty()) throw new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);

        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get products success.")
                        .data(products.stream()
                                .map(productMapper::toProductResponse)
                                .toList()
                        )
                        .build()
        );
    }

    @GetMapping(name = "/by-category")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategoryName(category);

        if (products.isEmpty()) throw new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);

        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get products success.")
                        .data(products.stream()
                                .map(productMapper::toProductResponse)
                                .toList()
                        )
                        .build()
        );
    }

    @GetMapping(name = "/count/by-brand")
    public ResponseEntity<ApiResponse<Long>> countProductsByBrand(@RequestParam String brand) {
        Long count = productService.countProductsByBrand(brand);

        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .code(HttpStatus.OK.value())
                        .message("Count products success.")
                        .data(count)
                        .build()
        );
    }

    @GetMapping(name = "/count/by-category")
    public ResponseEntity<ApiResponse<Long>> countProductsByCategory(@RequestParam String category) {
        Long count = productService.countProductsByCategoryName(category);

        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .code(HttpStatus.OK.value())
                        .message("Count products success.")
                        .data(count)
                        .build()
        );
    }
}
