DROP TABLE IF EXISTS mpa_ratings CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS mpa_ratings
(
    mpa_id    INT auto_increment    PRIMARY KEY,
    mpa_name  VARCHAR(255)          NOT NULL
);
DROP TABLE IF EXISTS genres CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS genres
(
    genre_id      INT auto_increment    PRIMARY KEY,
    genre_name    VARCHAR(255)          NOT NULL
);
DROP TABLE IF EXISTS films CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS films
(
    id              INT auto_increment    PRIMARY KEY,
    name            VARCHAR(255)          NOT NULL,
    description     VARCHAR(255)          NOT NULL,
    release_date    DATE                  NOT NULL,
    duration        INT                   NOT NULL,
    mpa_rating_id   INT,
    FOREIGN KEY (mpa_rating_id) REFERENCES mpa_ratings (mpa_id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS film_genres CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  INT REFERENCES films (id) ON DELETE CASCADE,
    genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
    CONSTRAINT film_genres_pk PRIMARY KEY (film_id, genre_id)
);

DROP TABLE IF EXISTS users CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS users
(
    id       INT auto_increment    PRIMARY KEY,
    login    VARCHAR(255)          NOT NULL,
    name     VARCHAR(255)          NOT NULL UNIQUE,
    email    VARCHAR(255)          NOT NULL UNIQUE,
    birthday DATE                  NOT NULL
);

DROP TABLE IF EXISTS friendships CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS friendships
(
    id        INT auto_increment PRIMARY KEY,
    user_id   INT NOT NULL,
    friend_id INT NOT NULL,
    status BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE
);
DROP TABLE IF EXISTS likes CASCADE; -- удаляем таблицу перед созданием для тестов Postman
CREATE TABLE IF NOT EXISTS likes
(
    film_id INT REFERENCES films (id) ON DELETE CASCADE,
    user_id INT REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT likes_pk PRIMARY KEY (film_id, user_id)
);
