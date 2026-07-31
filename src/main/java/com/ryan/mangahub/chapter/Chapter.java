package com.ryan.mangahub.chapter;

import com.ryan.mangahub.manga.Manga;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;


    @Column(name = "chapter_number", nullable = false)
    private Integer chapterNumber;

    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    protected Chapter() {
    }

    public Chapter(Manga manga, Integer chapterNumber, String title) {
        this.manga = manga;
        this.chapterNumber = chapterNumber;
        this.title = title;
    }

    // Setter Getter

    public Long getId() {
        return id;
    }
    public Manga getManga() {
        return manga;
    }
    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
