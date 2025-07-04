package com.academy.cinemaxx.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PaginationResponseDTO<TData> (
        @Schema(example = "10")
        int limit,
        @Schema(example = "1")
        int pages,
        @Schema(example = "100")
        long elements,
        @Schema(description = "The list of paginated data")
        List<TData> data
) {}
