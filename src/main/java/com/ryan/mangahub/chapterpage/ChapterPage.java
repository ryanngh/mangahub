package com.ryan.mangahub.chapterpage;

import com.ryan.mangahub.chapter.Chapter;
import jakarta.persistence.*;

@Entity
@Table(name = "chapter_pages")
public class ChapterPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;
    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected ChapterPage() {}
    public ChapterPage(Chapter chapter, Integer pageNumber, String imageUrl) {
        this.chapter = chapter;
        this.pageNumber = pageNumber;
        this.imageUrl = imageUrl;
    }

    // Setter Getter

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Chapter getChapter() {
        return chapter;
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public Integer getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
