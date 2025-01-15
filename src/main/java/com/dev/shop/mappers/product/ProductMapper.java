package com.dev.shop.mappers.product;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.domain.dtos.requests.ProductCreateRequest;
import com.dev.shop.domain.dtos.requests.ProductUpdateRequest;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements IProductMapper {

    private final CategoryRepository categoryRepository;

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

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));

        product.setCategory(category);

        return product;
    }
}
