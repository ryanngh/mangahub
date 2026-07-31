package com.ryan.mangahub.chapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    @Query("""
            SELECT c FROM Chapter c WHERE c.manga.id = :mangaId
            """)
    List<Chapter> getAllByMangaId(@Param("mangaId") Long mangaId);

    @Query("""
            SELECT c FROM Chapter c WHERE c.manga.id = :mangaId AND c.chapterNumber = :chapterNumber 
            """)
    Optional<Chapter> getByMangaIdAndChapterNumber(
            @Param("mangaId") Long mangaId,
            @Param("chapterNumber") Integer chapterNumber
    );
}
