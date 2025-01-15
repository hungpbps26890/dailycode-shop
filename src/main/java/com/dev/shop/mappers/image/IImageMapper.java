package com.dev.shop.mappers.image;

import com.dev.shop.domain.dtos.responses.ImageResponse;
import com.dev.shop.domain.entities.Image;

public interface IImageMapper {

    ImageResponse toImageResponse(Image image);
}
