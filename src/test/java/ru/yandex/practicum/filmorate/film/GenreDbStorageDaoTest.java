package ru.yandex.practicum.filmorate.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.film.GenreStorageDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenreDbStorageDaoTest {
    private final GenreStorageDao genreStorageDao;
    private final FilmStorageDao filmStorage;
    private final List<Genre> genres = List.of(new Genre(1, "Комедия"),
            new Genre(2, "Драма"),
            new Genre(3, "Мультфильм"),
            new Genre(4, "Триллер"),
            new Genre(5, "Документальный"),
            new Genre(6, "Боевик"));


    @Test
    void getAllGenreTest() {
        assertEquals(genres, genreStorageDao.findAll());
    }

    @Test
    void findGenreByIdTest() {
        assertThat(genreStorageDao.findById(5))
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("name", "Документальный")
                );
    }

    @Test
    void setAndLoadFilmGenreTest() {
        final Film film1 = new Film(1, "Пираты Карибского моря: Проклятие Черной жемчужины",
                "Пират нападает на армию мертвецов, чтобы вернуть свой корабль. " +
                        "Боевик о первых приключениях Джека Воробья",
                LocalDate.of(2003, 8, 22),
                143, new MpaRating(3, "PG-13"),
                Set.of(genres.get(5)));
        filmStorage.create(film1);
        genreStorageDao.setFilmGenre(film1);

        assertEquals(1, genreStorageDao.loadFilmGenre(film1).size());
    }
}