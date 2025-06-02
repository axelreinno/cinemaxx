package com.academy.cinemaxx.dtos;

import java.util.List;

public record PaginationResponseDTO<TData> (
        int limit,
        int pages,
        long elements,
        List<TData> data
) {}
