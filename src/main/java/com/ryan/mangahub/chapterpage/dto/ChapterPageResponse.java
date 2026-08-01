package com.ryan.mangahub.chapterpage.dto;

import com.ryan.mangahub.chapterpage.ChapterPage;

public record ChapterPageResponse(
        Long id,
        Long chapterId,
        Integer pageNumber,
        String imageUrl
) {
    public static ChapterPageResponse from(ChapterPage chapterPage) {
        return new ChapterPageResponse(
                chapterPage.getId(),
                chapterPage.getChapter().getId(),
                chapterPage.getPageNumber(),
                chapterPage.getImageUrl()
        );
    }
}
