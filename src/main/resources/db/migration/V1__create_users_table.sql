CREATE TABLE users
(
    id             BIGSERIAL PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    email          VARCHAR(255) NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    display_name   VARCHAR(100),
    avatar_url     VARCHAR(500),

    role           VARCHAR(20)  NOT NULL DEFAULT 'USER',   -- USER, ADMIN, AUTHOR, TRANSLATOR, EDITOR, MODERATOR --
    status         VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, BANNED, PENDING_VERIFY --

    email_verified BOOLEAN      NOT NULL DEFAULT FALSE,

    created_at     TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT now(),
    last_login_at  TIMESTAMP
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_username ON users (username);