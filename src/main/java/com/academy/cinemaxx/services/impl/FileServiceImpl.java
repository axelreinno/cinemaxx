package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.configs.MinioConfig;
import com.academy.cinemaxx.dtos.PresignedUrlResponseDto;
import com.academy.cinemaxx.services.FileService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl implements FileService {
    private MinioClient minioClient;
    private MinioConfig minioConfig;

    public FileServiceImpl(MinioClient minioClient, MinioConfig minioConfig) {
        super();
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    public PresignedUrlResponseDto generatePresignedUploadUrl(String filename) {
        try {
            String extension = getExtension(filename);
            String objectKey = UUID.randomUUID() + (extension != null ? "." + extension : "");

            String uploadUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(minioConfig.getBucketName())
                            .object(objectKey)
                            .expiry(10, TimeUnit.MINUTES)
                            .build()
            );

            String publicUrl = String.format("%s/%s/%s", minioConfig.getUrl(), minioConfig.getBucketName(), objectKey);

            return new PresignedUrlResponseDto(uploadUrl, objectKey, publicUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signed URL", e);
        }
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot >= 0 ? filename.substring(dot + 1) : null;
    }
}
