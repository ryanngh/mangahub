package com.ryan.mangahub.manga;

import com.ryan.mangahub.manga.dto.MangaRequest;
import com.ryan.mangahub.manga.dto.MangaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MangaResponse>> getAll() {
        return ResponseEntity.ok(mangaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MangaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mangaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MangaResponse> createManga(@RequestBody MangaRequest mangaRequest, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MangaResponse response = mangaService.createManga(mangaRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MangaResponse> updateManga(@PathVariable Long id, @RequestBody MangaRequest request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        MangaResponse response = mangaService.updateManga(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        mangaService.deleteManga(id, userId);
        return ResponseEntity.noContent().build();
    }

}
