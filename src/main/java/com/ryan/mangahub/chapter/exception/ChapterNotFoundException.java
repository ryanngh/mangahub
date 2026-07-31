package com.ryan.mangahub.chapter.exception;

public class ChapterNotFoundException extends RuntimeException {
    public ChapterNotFoundException(Long id) {
        super("Could not find chapter with id " + id);
    }
}