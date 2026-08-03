package com.ryan.mangahub.chapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record ChapterRequest(
        @NotNull
        @Positive
        Integer chapterNumber,
        @NotBlank
        String title
) {
}
