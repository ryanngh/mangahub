package com.ryan.mangahub.chapterpage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChapterPageRequest(
        @NotNull
        @Positive
        Integer pageNumber,
        @NotBlank
        String imageUrl
){
}
