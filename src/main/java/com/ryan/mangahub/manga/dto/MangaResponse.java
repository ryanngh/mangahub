package com.ryan.mangahub.manga.dto;

import com.ryan.mangahub.manga.Manga;
import com.ryan.mangahub.manga.Status;

import java.time.LocalDateTime;

public record MangaResponse(
        Long id,
        String title,
        String description,
        String coverUrl, Long uploadedBy,
        Status status,
        Long viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MangaResponse from(Manga manga) {
        return new MangaResponse(
                manga.getId(),
                manga.getTitle(),
                manga.getDescription(),
                manga.getCoverUrl(),
                manga.getUploadedBy().getId(),
                manga.getStatus(),
                manga.getViewCount(),
                manga.getCreatedAt(),
                manga.getUpdatedAt()
        );
    }
}
