package com.dev.shop.mappers.product;

import com.dev.shop.domain.entities.Product;
import com.dev.shop.dtos.requests.ProductCreateRequest;
import com.dev.shop.dtos.requests.ProductUpdateRequest;

public interface IProductMapper {

    Product toProduct(ProductCreateRequest request);

    Product updateProduct(Product product, ProductUpdateRequest request);
}
