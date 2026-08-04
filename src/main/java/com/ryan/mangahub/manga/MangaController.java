package com.ryan.mangahub.manga;

import com.ryan.mangahub.common.dto.SliceResponse;
import com.ryan.mangahub.manga.dto.MangaRequest;
import com.ryan.mangahub.manga.dto.MangaResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manga")
public class MangaController {
    private MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping
    public ResponseEntity<SliceResponse<MangaResponse>> getAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        Slice<MangaResponse> slice = mangaService.getAll(pageable);
        return ResponseEntity.ok(SliceResponse.from(slice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MangaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mangaService.getById(id));
    }

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<MangaResponse> createManga(@Valid @RequestBody MangaRequest mangaRequest, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MangaResponse response = mangaService.createManga(mangaRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MangaResponse> updateManga(@PathVariable Long id, @Valid @RequestBody MangaRequest request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MangaResponse response = mangaService.updateManga(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        mangaService.deleteManga(id, userId);
        return ResponseEntity.noContent().build();
    }

}
