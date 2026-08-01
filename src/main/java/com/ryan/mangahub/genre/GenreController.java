package com.ryan.mangahub.genre;

import com.ryan.mangahub.genre.dto.GenreRequest;
import com.ryan.mangahub.genre.dto.GenreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // === Genre CRUD: /genre ===

    @GetMapping("/genre")
    public ResponseEntity<List<GenreResponse>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<GenreResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @PostMapping("/genre")
    public ResponseEntity<GenreResponse> create(@RequestBody GenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/genre/{id}")
    public ResponseEntity<GenreResponse> update(@PathVariable Long id, @RequestBody GenreRequest request) {
        return ResponseEntity.ok(genreService.updateGenre(id, request));
    }

    @DeleteMapping("/genre/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    // === Manga-Genre relationship: /manga/{mangaId}/genre ===

    @GetMapping("/manga/{mangaId}/genre")
    public ResponseEntity<List<GenreResponse>> getGenresByManga(@PathVariable Long mangaId) {
        return ResponseEntity.ok(genreService.getGenresByMangaId(mangaId));
    }

    @PostMapping("/manga/{mangaId}/genre/{genreId}")
    public ResponseEntity<Void> addGenreToManga(@PathVariable Long mangaId,
                                                 @PathVariable Long genreId,
                                                 Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        genreService.addGenreToManga(mangaId, genreId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/manga/{mangaId}/genre/{genreId}")
    public ResponseEntity<Void> removeGenreFromManga(@PathVariable Long mangaId,
                                                      @PathVariable Long genreId,
                                                      Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        genreService.removeGenreFromManga(mangaId, genreId, userId);
        return ResponseEntity.noContent().build();
    }
}
