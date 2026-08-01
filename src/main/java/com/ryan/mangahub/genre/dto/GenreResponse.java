package com.ryan.mangahub.genre.dto;

import com.ryan.mangahub.genre.Genre;

public record GenreResponse(
        Long id,
        String name
) {
    public static GenreResponse from(Genre genre) {
        return new GenreResponse(
                genre.getId(),
                genre.getName()
        );
    }
}
