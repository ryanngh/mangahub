package com.ryan.mangahub.chapter;

import com.ryan.mangahub.chapter.dto.ChapterRequest;
import com.ryan.mangahub.chapter.dto.ChapterResponse;
import com.ryan.mangahub.chapter.exception.ChapterNotFoundException;
import com.ryan.mangahub.manga.Manga;
import com.ryan.mangahub.manga.MangaRepository;
import com.ryan.mangahub.manga.exception.MangaNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final MangaRepository mangaRepository;

    public ChapterService(ChapterRepository chapterRepository, MangaRepository mangaRepository) {
        this.chapterRepository = chapterRepository;
        this.mangaRepository = mangaRepository;
    }

    public List<ChapterResponse> getChaptersByMangaId(Long mangaId) {
        List<Chapter> chapters = chapterRepository.getAllByMangaId(mangaId);

        return chapters.stream()
                .map(ChapterResponse::from)
                .toList();
    }

    public ChapterResponse getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ChapterNotFoundException(id));
        return ChapterResponse.from(chapter);
    }

    public ChapterResponse createChapter(Long mangaId, ChapterRequest request, Long userId) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new MangaNotFoundException(mangaId));
        if (!manga.getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        Chapter chapter = new Chapter(manga, request.chapterNumber(), request.title());
        chapterRepository.save(chapter);
        return ChapterResponse.from(chapter);
    }

    public ChapterResponse updateChapter(Long id, ChapterRequest request, Long userId) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ChapterNotFoundException(id));
        if (!chapter.getManga().getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        chapter.setChapterNumber(request.chapterNumber());
        chapter.setTitle(request.title());
        chapterRepository.save(chapter);
        return ChapterResponse.from(chapter);
    }

    public void deleteChapter(Long id, Long userId) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ChapterNotFoundException(id));
        if (!chapter.getManga().getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        chapterRepository.deleteById(id);
    }
}
