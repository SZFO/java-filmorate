package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.LikeMapper;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LikeDbStorageDaoImpl implements LikeStorageDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorageDao userStorage;
    private final FilmStorageDao filmStorage;

    @Override
    public boolean addLike(int filmId, int userId) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";

        return jdbcTemplate.update(sql, filmId, userId) != 0;
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";

        return jdbcTemplate.update(sql, filmId, userId) != 0;
    }

    @Override
    public List<Like> getLikes(int filmId, int userId) {
        String sql = "SELECT l.film_id, l.user_id FROM likes AS l WHERE l.film_id = ? AND l.user_id = ? ";

        return jdbcTemplate.query(sql, new LikeMapper(userStorage, filmStorage), filmId, userId);
    }
}