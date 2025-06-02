package com.academy.cinemaxx.dtos;

public record PresignedUrlResponseDto(
        String uploadUrl,
        String filename,
        String publicUrl
) {
}
