package com.academy.cinemaxx.dtos.response;

import java.util.List;

public record PaginationResponseDTO<TData> (
        int limit,
        int pages,
        long elements,
        List<TData> data
) {}
