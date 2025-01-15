package com.dev.shop.controllers;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.ImageResponse;
import com.dev.shop.domain.entities.Image;
import com.dev.shop.exceptions.ImageException;
import com.dev.shop.services.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(name = "${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<ImageResponse>>> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId
    ) {
        return ResponseEntity.ok(
                ApiResponse.<List<ImageResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Upload success.")
                        .data(imageService.saveImages(files, productId))
                        .build()
        );
    }

    @GetMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<Resource>> downloadImage(@PathVariable("id") Long id) {
        Image image = imageService.getImageById(id);

        ByteArrayResource resource = null;

        try {
            resource = new ByteArrayResource(image
                    .getImage()
                    .getBytes(1, (int) image.getImage().length()));
        } catch (SQLException e) {
            throw new ImageException(ErrorMessage.IMAGE_DOWNLOAD_FAILED);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\""
                )
                .body(ApiResponse.<Resource>builder()
                        .code(HttpStatus.OK.value())
                        .message("Download success.")
                        .data(resource)
                        .build());
    }

    @PutMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<?>> updateImage(
            @PathVariable("id") Long id,
            @RequestBody MultipartFile file
    ) {
        imageService.updateImage(id, file);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update success.")
                        .build()
        );
    }

    @DeleteMapping(name = "/{id}")
    public ResponseEntity<ApiResponse<?>> updateImage(
            @PathVariable("id") Long id
    ) {
        imageService.deleteImageById(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete success.")
                        .build()
        );
    }

}
