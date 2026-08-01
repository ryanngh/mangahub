package com.ryan.mangahub.chapterpage.exception;

public class ChapterPageNotFoundException extends RuntimeException {
    public ChapterPageNotFoundException(Long id) { super("Chapter Page with id " + id + " not found"); }
}
