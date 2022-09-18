package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class LikeMapper implements RowMapper<Like> {
    private final UserStorageDao userStorage;
    private final FilmStorageDao filmStorage;

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Like(
                filmStorage.getById(rs.getInt("film_id")).get(),
                userStorage.getById(rs.getInt("user_id")).get());
    }
}