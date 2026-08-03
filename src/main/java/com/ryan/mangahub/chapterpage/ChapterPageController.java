package com.ryan.mangahub.chapterpage;

import com.ryan.mangahub.chapterpage.dto.ChapterPageRequest;
import com.ryan.mangahub.chapterpage.dto.ChapterPageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter/{chapterId}/page")

public class ChapterPageController {
    private final ChapterPageService chapterPageService;

    public ChapterPageController(ChapterPageService chapterPageService) {
        this.chapterPageService = chapterPageService;
    }

    @GetMapping
    public ResponseEntity<List<ChapterPageResponse>> getAllByChapterId(@PathVariable Long chapterId) {
        return ResponseEntity.ok(chapterPageService.getAllByChapterId(chapterId));
    }

    @PostMapping
    public ResponseEntity<ChapterPageResponse> createChapterPage(@PathVariable Long chapterId,
                                                                 @Valid @RequestBody ChapterPageRequest request,
                                                                 Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        ChapterPageResponse response = chapterPageService.createPage(chapterId, request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapterPageResponse> updatePage(@PathVariable Long chapterId, @PathVariable Long id, @Valid @RequestBody ChapterPageRequest request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        ChapterPageResponse response = chapterPageService.updatePage(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long chapterId, @PathVariable Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        chapterPageService.deletePage(id, userId);
        return ResponseEntity.noContent().build();
    }

}
