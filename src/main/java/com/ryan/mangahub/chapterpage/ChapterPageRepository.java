package com.ryan.mangahub.chapterpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterPageRepository extends JpaRepository<ChapterPage, Long> {
    @Query("""
            SELECT cp 
            FROM ChapterPage cp 
            WHERE cp.chapter.id = :chapterId 
            ORDER BY cp.pageNumber""")
    List<ChapterPage> getAllByChapterId(@Param("chapterId") Long chapterId);
}