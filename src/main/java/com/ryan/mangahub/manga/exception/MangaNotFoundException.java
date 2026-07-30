package com.ryan.mangahub.manga.exception;

public class MangaNotFoundException extends RuntimeException {
    public MangaNotFoundException(Long id) {
        super("Could not find manga with id " + id);
    }
}