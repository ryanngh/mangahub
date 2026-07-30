package com.ryan.mangahub.manga.dto;

public record MangaRequest(
        String title,
        String description,
        String coverUrl
) {
}
