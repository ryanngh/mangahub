package com.ryan.mangahub.chapter;

import com.ryan.mangahub.chapter.dto.ChapterRequest;
import com.ryan.mangahub.chapter.dto.ChapterResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manga/{mangaId}/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<List<ChapterResponse>> getAllByMangaId(@PathVariable Long mangaId) {
        return ResponseEntity.ok(chapterService.getChaptersByMangaId(mangaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterResponse> getById(@PathVariable Long mangaId, @PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PostMapping
    public ResponseEntity<ChapterResponse> create(@PathVariable Long mangaId,
                                                  @Valid @RequestBody ChapterRequest request,
                                                  Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        ChapterResponse response = chapterService.createChapter(mangaId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapterResponse> update(@PathVariable Long mangaId,
                                                  @PathVariable Long id,
                                                  @Valid @RequestBody ChapterRequest request,
                                                  Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        ChapterResponse response = chapterService.updateChapter(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long mangaId,
                                       @PathVariable Long id,
                                       Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        chapterService.deleteChapter(id, userId);
        return ResponseEntity.noContent().build();
    }
}
