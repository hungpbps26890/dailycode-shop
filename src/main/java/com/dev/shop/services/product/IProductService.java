package com.dev.shop.services.product;

import com.dev.shop.domain.entities.Product;
import com.dev.shop.domain.dtos.requests.ProductCreateRequest;
import com.dev.shop.domain.dtos.requests.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product createProduct(ProductCreateRequest request);

    Product getProductById(Long id);

    Product getProductByName(String name);

    Product updateProduct(Long id, ProductUpdateRequest request);

    void deleteProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategoryName(String categoryName);

    List<Product> getProductsByBrand(String brand);

    Long countProductsByBrand(String brand);

    Long countProductsByCategoryName(String categoryName);
}
