package com.dev.shop.mappers.product;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.ProductResponse;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.domain.dtos.requests.ProductCreateRequest;
import com.dev.shop.domain.dtos.requests.ProductUpdateRequest;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.category.ICategoryMapper;
import com.dev.shop.mappers.image.IImageMapper;
import com.dev.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductMapper implements IProductMapper {

    private final CategoryRepository categoryRepository;

    private final ICategoryMapper categoryMapper;

    private final IImageMapper imageMapper;

    @Override
    public Product toProduct(ProductCreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .inventory(request.getInventory())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
    }

    @Override
    public Product updateProduct(Product product, ProductUpdateRequest request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .inventory(product.getInventory())
                .description(product.getDescription())
                .category(categoryMapper.toCategoryResponse(product.getCategory()))
                .images(Optional.ofNullable(product.getImages())
                        .map(images -> images.stream()
                                .map(imageMapper::toImageResponse)
                                .toList()
                        ).orElse(null)
                )
                .build();
    }
}
