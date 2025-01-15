package com.dev.shop.mappers.image;

import com.dev.shop.domain.dtos.responses.ImageResponse;
import com.dev.shop.domain.entities.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements IImageMapper{

    @Override
    public ImageResponse toImageResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .downloadUrl(image.getDownloadUrl())
                .build();
    }
}
