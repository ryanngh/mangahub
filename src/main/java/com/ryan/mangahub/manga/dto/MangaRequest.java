package com.ryan.mangahub.manga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MangaRequest(
        @NotBlank
        @Size(min = 2, max = 255)
        String title,
        @Size(max = 1000)
        String description,
        String coverUrl
) {
}
