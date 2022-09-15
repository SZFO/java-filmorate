package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class FilmDbStorageDaoImpl implements FilmStorageDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> findAll() {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, mr.mpa_id, mr.mpa_name " +
                "FROM films AS f JOIN mpa_ratings AS mr ON f.mpa_rating_id = mr.mpa_id";

        return jdbcTemplate.query(sql, new FilmMapper());
    }


    @Override
    public Film create(Film film) {
        String sql = "INSERT INTO films (name, description, release_date, duration, mpa_rating_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(filmId);

        return film;
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        String sql = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_rating_id = ? WHERE id = ?";

        return jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId()) == 0 ?
                Optional.empty() : Optional.of(film);
    }

    @Override
    public void deleteFilm(int id) {
        String sql = "DELETE FROM films WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, mr.mpa_id, mr.mpa_name " +
                "FROM films AS f JOIN mpa_ratings AS mr ON f.mpa_rating_id = mr.mpa_id WHERE f.id = ?";
        List<Film> films = jdbcTemplate.query(sql, new FilmMapper(), id);

        return films.size() == 0 ? Optional.empty() : Optional.of(films.get(0));
    }

    @Override
    public List<Film> getPopular(int count) {
        String sql = "SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, mr.MPA_ID, mr.MPA_NAME, " +
                "g.GENRE_NAME , count(L.USER_ID) AS user_likes " +
                "FROM FILMS F JOIN MPA_RATINGS mr ON mr.MPA_ID = F.MPA_RATING_ID " +
                "LEFT JOIN FILM_GENRES fg ON F.ID  = fg.FILM_ID " +
                "LEFT JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID " +
                "LEFT JOIN LIKES L ON F.ID = L.FILM_ID " +
                "GROUP BY F.ID, fg.GENRE_ID ORDER BY user_likes DESC LIMIT ?";

        return jdbcTemplate.query(sql, new FilmMapper(), count);
    }
}