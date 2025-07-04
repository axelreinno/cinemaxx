package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlResponseDto(
        @Schema(example = "https://bucket.com")
        String uploadUrl,
        @Schema(example = "e9b13740-0371-42a5-85e1-68baa68218c7.jpg")
        String filename
) {
}
