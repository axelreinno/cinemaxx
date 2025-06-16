package com.academy.cinemaxx.dtos.response;

public record PresignedUrlResponseDto(
        String uploadUrl,
        String filename
) {
}
