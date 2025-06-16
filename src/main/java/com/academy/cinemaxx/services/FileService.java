package com.academy.cinemaxx.services;

import com.academy.cinemaxx.dtos.response.PresignedUrlResponseDto;

public interface FileService {
    public PresignedUrlResponseDto generatePresignedUploadUrl(String filename);
}
