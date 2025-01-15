package com.dev.shop.domain.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer inventory;
    private String description;
    private CategoryResponse category;
    private List<ImageResponse> images;
}
