package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MpaRatingDbStorageDaoImpl implements MpaRatingStorageDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaRatingDbStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MpaRating> getAll() {
        String sql = "SELECT * FROM mpa_ratings";

        return jdbcTemplate.query(sql, new MpaMapper());
    }

    @Override
    public Optional<MpaRating> getById(int id) {
        String sql = "SELECT * FROM mpa_ratings WHERE mpa_id = ?";
        List<MpaRating> ratings = jdbcTemplate.query(sql, new MpaMapper(), id);

        return ratings.size() == 0 ? Optional.empty() : Optional.of(ratings.get(0));
    }
}