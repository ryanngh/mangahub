package com.ryan.mangahub.common.dto;

import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        int pageNo,
        int pageSize,
        boolean hasNext,
        boolean isFirst,
        boolean isLast
) {
    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                slice.getNumber(),
                slice.getSize(),
                slice.hasNext(),
                slice.isFirst(),
                slice.isLast()
        );
    }
}
