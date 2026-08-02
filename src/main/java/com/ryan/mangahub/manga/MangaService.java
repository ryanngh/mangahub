package com.ryan.mangahub.manga;

import com.ryan.mangahub.manga.dto.MangaRequest;
import com.ryan.mangahub.manga.dto.MangaResponse;
import com.ryan.mangahub.manga.exception.MangaNotFoundException;
import com.ryan.mangahub.user.Role;
import com.ryan.mangahub.user.User;
import com.ryan.mangahub.user.UserRepository;
import com.ryan.mangahub.user.exception.UserNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService {
    private final MangaRepository mangaRepository;
    private final UserRepository userRepository;

    public MangaService(MangaRepository mangaRepository, UserRepository userRepository) {
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
    }

    public MangaResponse getById(Long id) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id));
        return MangaResponse.from(manga);
    }

    public List<MangaResponse> getAll() {
        return mangaRepository.findAll()
                .stream()
                .map(MangaResponse::from)
                .toList();
    }

    public MangaResponse createManga(MangaRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getRole() != Role.AUTHOR) {
            throw new AccessDeniedException("Only Author can create manga");
        }
        Manga manga = new Manga(request.title(), request.description(), request.coverUrl(), user);
        mangaRepository.save(manga);
        return MangaResponse.from(manga);
    }

    public MangaResponse updateManga(Long id, MangaRequest request, Long userId) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id));
        if (!manga.getUploadedBy().getId().equals(userId)) {
            throw new AccessDeniedException("You don't own this manga");
        }
        manga.setTitle(request.title());
        manga.setDescription(request.description());
        manga.setCoverUrl(request.coverUrl());
        mangaRepository.save(manga);
        return MangaResponse.from(manga);
    }

    public void deleteManga(Long id, Long userId) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id));
        if (!manga.getUploadedBy().getId().equals(userId)) {
            throw new RuntimeException("You don't own this manga");
        }
        mangaRepository.deleteById(id);
    }
}
