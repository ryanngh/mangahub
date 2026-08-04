package com.ryan.mangahub.manga;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MangaRepository extends JpaRepository<Manga, Long> {

    @Query("SELECT m FROM Manga m JOIN FETCH m.uploadedBy")
    Slice<Manga> findAllBy(Pageable pageable);

    @Query("SELECT m FROM Manga m JOIN FETCH m.uploadedBy WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Slice<Manga> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM Manga m JOIN FETCH m.uploadedBy WHERE m.status = :status")
    Slice<Manga> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query("SELECT m FROM Manga m JOIN FETCH m.uploadedBy WHERE m.uploadedBy.id = :userId")
    Slice<Manga> findByUploadedById(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM Manga m JOIN FETCH m.uploadedBy ORDER BY m.viewCount DESC")
    Slice<Manga> findTopByViews(Pageable pageable);
}