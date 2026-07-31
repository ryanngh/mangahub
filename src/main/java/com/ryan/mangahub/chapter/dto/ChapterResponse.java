package com.ryan.mangahub.chapter.dto;

import com.ryan.mangahub.chapter.Chapter;

import java.time.LocalDateTime;

public record ChapterResponse(
        Long id,
        Long mangaId,
        Integer chapterNumber,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ChapterResponse from(Chapter chapter) {
        return new ChapterResponse(
                chapter.getId(),
                chapter.getManga().getId(),
                chapter.getChapterNumber(),
                chapter.getTitle(),
                chapter.getCreatedAt(),
                chapter.getUpdatedAt()
        );
    }
}
