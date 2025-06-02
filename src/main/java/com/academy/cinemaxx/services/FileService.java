package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.PresignedUrlResponseDto;

public interface FileService {
    public PresignedUrlResponseDto generatePresignedUploadUrl(String filename);
}
