package com.ryan.mangahub.manga;

import com.ryan.mangahub.genre.Genre;
import com.ryan.mangahub.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;
    private String description;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ONGOING; //  -- ONGOING, COMPLETED, HIATUS, DROPPED --

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @ManyToMany
    @JoinTable(
            name = "manga_genres",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Manga() {}
    public Manga(String title, String description, String coverUrl, User uploadedBy) {
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.uploadedBy = uploadedBy;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getter

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getCoverUrl() {
        return coverUrl;
    }
    public User getUploadedBy() {
        return uploadedBy;
    }

    public Status getStatus() {
        return status;
    }

    public Long getViewCount() {
        return viewCount;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public Set<Genre> getGenres() {
        return genres;
    }

    // Setter

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

}
