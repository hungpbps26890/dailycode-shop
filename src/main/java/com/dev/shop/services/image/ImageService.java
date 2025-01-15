package com.dev.shop.services.image;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.ImageResponse;
import com.dev.shop.domain.entities.Image;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.repositories.ImageRepository;
import com.dev.shop.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;

    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.IMAGE_NOT_FOUND));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotFoundException(ErrorMessage.IMAGE_NOT_FOUND);
                });
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        Image image = getImageById(id);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));

            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ImageResponse> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageResponse> imageResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String buildDownloadUrl = "/api/v1/images/download/";

                Image imageToSave = Image.builder()
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .downloadUrl(buildDownloadUrl)
                        .product(product)
                        .build();

                Image savedImage = imageRepository.save(imageToSave);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                imageRepository.save(savedImage);

                ImageResponse imageResponse = ImageResponse.builder()
                        .id(savedImage.getId())
                        .fileName(savedImage.getFileName())
                        .downloadUrl(savedImage.getDownloadUrl())
                        .build();

                imageResponses.add(imageResponse);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        return imageResponses;
    }
}
