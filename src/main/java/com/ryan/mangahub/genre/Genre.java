package com.ryan.mangahub.genre;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    protected Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    // Getter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter

    public void setName(String name) {
        this.name = name;
    }
}
