package com.ryan.mangahub.manga;

import com.ryan.mangahub.manga.dto.MangaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Long> {
    @Query("""
                SELECT m 
                FROM Manga m
                WHERE LOWER(m.title) LIKE LOWER(CONCAT('%',:keyword,'%'))
            """)
    List<Manga> searchByTitle(@Param("keyword") String keyword);

    @Query("""
            SELECT m
            FROM Manga m
            WHERE m.status = :status
            """)
    List<Manga> findByStatus(@Param("status") Status status);

    @Query("""
            SELECT m
            FROM Manga m
            WHERE m.uploadedBy.id = :userId
            """)
    List<Manga> findByUploadedBy(@Param("userId") Long userId);

    @Query("""
            SELECT m
            FROM Manga m 
            ORDER BY m.viewCount DESC""")
    List<Manga> findTopByViews();
}