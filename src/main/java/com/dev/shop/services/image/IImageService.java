package com.dev.shop.services.image;

import com.dev.shop.domain.dtos.responses.ImageResponse;
import com.dev.shop.domain.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    void updateImage(Long id, MultipartFile file);

    List<ImageResponse> saveImages(List<MultipartFile> files, Long productId);
}
