CREATE TABLE mangas
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    cover_url   VARCHAR(500),
    uploaded_by BIGINT       NOT NULL REFERENCES users (id),
    -- ONGOING, COMPLETED, HIATUS, DROPPED --
    status      VARCHAR(20)  NOT NULL DEFAULT 'ONGOING',
    view_count  BIGINT       NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX idx_mangas_uploaded_by ON mangas (uploaded_by);
CREATE INDEX idx_mangas_title ON mangas (title);


CREATE TABLE chapters
(
    id             BIGSERIAL PRIMARY KEY,
    manga_id       BIGINT       NOT NULL REFERENCES mangas (id),
    chapter_number INT          NOT NULL,
    title          VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT uq_chapters_manga_number UNIQUE (manga_id, chapter_number)
);

CREATE TABLE chapter_pages
(
    id          BIGSERIAL PRIMARY KEY,
    chapter_id  BIGINT       NOT NULL REFERENCES chapters (id),
    page_number INT          NOT NULL,
    image_url   VARCHAR(500) NOT NULL,
    CONSTRAINT uq_chapter_pages_chapter_number UNIQUE (chapter_id, page_number)
);

CREATE INDEX idx_chapter_pages_chapter_id ON chapter_pages (chapter_id);

CREATE TABLE genres
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE manga_genres
(
    manga_id BIGINT NOT NULL REFERENCES mangas (id) ON DELETE CASCADE,
    genre_id BIGINT NOT NULL REFERENCES genres (id) ON DELETE CASCADE,
    PRIMARY KEY (manga_id, genre_id)
);
