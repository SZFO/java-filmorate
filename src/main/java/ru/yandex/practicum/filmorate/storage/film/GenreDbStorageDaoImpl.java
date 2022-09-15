package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class GenreDbStorageDaoImpl implements GenreStorageDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genres";

        return jdbcTemplate.query(sql, new GenreMapper());
    }

    @Override
    public Optional<Genre> findById(int id) {
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper(), id);

        return genres.size() == 0 ? Optional.empty() : Optional.of(genres.get(0));
    }

    @Override
    public void setFilmGenre(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        String sqlQuery = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        film.getGenres().forEach(x -> jdbcTemplate.update(sql, film.getId(), x.getId()));
    }

    @Override
    public Set<Genre> loadFilmGenre(Film film) {
        String sql = "SELECT g.genre_id, g.genre_name " +
                "FROM film_genres AS fg " +
                "JOIN genres AS G ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper(), film.getId());

        return new HashSet<>(genres);
    }
}