package com.ryan.mangahub.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT g FROM Genre g WHERE g.name = :name")
    Optional<Genre> findByName(@Param("name") String name);
}
