package com.ryan.mangahub.genre.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(Long id) {
        super("Could not find genre with id " + id);
    }
}
