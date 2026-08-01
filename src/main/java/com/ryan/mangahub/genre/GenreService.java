package com.ryan.mangahub.genre;

import com.ryan.mangahub.genre.dto.GenreRequest;
import com.ryan.mangahub.genre.dto.GenreResponse;
import com.ryan.mangahub.genre.exception.GenreNotFoundException;
import com.ryan.mangahub.manga.Manga;
import com.ryan.mangahub.manga.MangaRepository;
import com.ryan.mangahub.manga.exception.MangaNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final MangaRepository mangaRepository;

    public GenreService(GenreRepository genreRepository, MangaRepository mangaRepository) {
        this.genreRepository = genreRepository;
        this.mangaRepository = mangaRepository;
    }

    // === Genre CRUD ===

    public List<GenreResponse> getAll() {
        return genreRepository.findAll()
                .stream()
                .map(GenreResponse::from)
                .toList();
    }

    public GenreResponse getById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(id));
        return GenreResponse.from(genre);
    }

    public GenreResponse createGenre(GenreRequest request) {
        Genre genre = new Genre(request.name());
        genreRepository.save(genre);
        return GenreResponse.from(genre);
    }

    public GenreResponse updateGenre(Long id, GenreRequest request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(id));
        genre.setName(request.name());
        genreRepository.save(genre);
        return GenreResponse.from(genre);
    }

    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreNotFoundException(id);
        }
        genreRepository.deleteById(id);
    }

    // === Manga-Genre relationship ===

    public List<GenreResponse> getGenresByMangaId(Long mangaId) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new MangaNotFoundException(mangaId));
        return manga.getGenres()
                .stream()
                .map(GenreResponse::from)
                .toList();
    }

    public void addGenreToManga(Long mangaId, Long genreId, Long userId) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new MangaNotFoundException(mangaId));
        if (!manga.getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
        manga.getGenres().add(genre);
        mangaRepository.save(manga);
    }

    public void removeGenreFromManga(Long mangaId, Long genreId, Long userId) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new MangaNotFoundException(mangaId));
        if (!manga.getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
        manga.getGenres().remove(genre);
        mangaRepository.save(manga);
    }
}
