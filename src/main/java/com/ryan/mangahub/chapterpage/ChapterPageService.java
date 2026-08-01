package com.ryan.mangahub.chapterpage;

import com.ryan.mangahub.chapter.Chapter;
import com.ryan.mangahub.chapter.ChapterRepository;
import com.ryan.mangahub.chapter.exception.ChapterNotFoundException;
import com.ryan.mangahub.chapterpage.dto.ChapterPageRequest;
import com.ryan.mangahub.chapterpage.dto.ChapterPageResponse;
import com.ryan.mangahub.chapterpage.exception.ChapterPageNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterPageService {
    private final ChapterPageRepository chapterPageRepository;
    private final ChapterRepository chapterRepository;

    public ChapterPageService(ChapterPageRepository chapterPageRepository, ChapterRepository chapterRepository) {
        this.chapterPageRepository = chapterPageRepository;
        this.chapterRepository = chapterRepository;
    }

    public List<ChapterPageResponse> getAllByChapterId(Long chapterId) {
        List<ChapterPage> pages = chapterPageRepository.getAllByChapterId(chapterId);
        return pages.stream()
                .map(ChapterPageResponse::from)
                .toList();
    }

    public ChapterPageResponse createPage(Long chapterId, ChapterPageRequest request, Long userId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(() -> new ChapterNotFoundException(chapterId));
        if (!chapter.getManga().getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        ChapterPage chapterPage = new ChapterPage(chapter, request.pageNumber(), request.imageUrl());
        chapterPageRepository.save(chapterPage);
        return ChapterPageResponse.from(chapterPage);
    }

    public ChapterPageResponse updatePage(Long id, ChapterPageRequest request, Long userId) {
        ChapterPage chapterPage = chapterPageRepository.findById(id)
                .orElseThrow(() -> new ChapterPageNotFoundException(id));
        if (!chapterPage.getChapter().getManga().getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        chapterPage.setPageNumber(request.pageNumber());
        chapterPage.setImageUrl(request.imageUrl());
        chapterPageRepository.save(chapterPage);
        return ChapterPageResponse.from(chapterPage);

    }

    public void deletePage(Long id, Long userId) {
        ChapterPage page = chapterPageRepository.findById(id)
                .orElseThrow(() -> new ChapterPageNotFoundException(id));
        if (!page.getChapter().getManga().getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        chapterPageRepository.deleteById(id);
    }
}
