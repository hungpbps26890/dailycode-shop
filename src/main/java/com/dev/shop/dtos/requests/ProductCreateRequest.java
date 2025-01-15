package com.dev.shop.dtos.requests;

import com.dev.shop.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer inventory;
    private String description;
    private Category category;
}
