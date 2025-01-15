package com.dev.shop.services.product;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.Category;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.dtos.requests.ProductCreateRequest;
import com.dev.shop.dtos.requests.ProductUpdateRequest;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.product.IProductMapper;
import com.dev.shop.repositories.CategoryRepository;
import com.dev.shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final IProductMapper productMapper;

    @Override
    public Product createProduct(ProductCreateRequest request) {
        String categoryName = request.getCategory().getName();

        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category categoryToCreate = Category.builder()
                            .name(categoryName)
                            .build();

                    return categoryRepository.save(categoryToCreate);
                });

        request.setCategory(category);

        Product productToCreate = productMapper.toProduct(request);

        return productRepository.save(productToCreate);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product updateProduct(Long id, ProductUpdateRequest request) {
        return productRepository.findById(id)
                .map(exsitingProduct -> productMapper.updateProduct(exsitingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public Long countProductsByBrand(String brand) {
        return productRepository.countByBrand(brand);
    }

    @Override
    public Long countProductsByCategoryName(String categoryName) {
        return productRepository.countByCategoryName(categoryName);
    }
}
