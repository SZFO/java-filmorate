package ru.yandex.practicum.filmorate.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.LikeStorageDao;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorageDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbStorageDaoTest {
    private final FilmStorageDao filmStorage;
    private final LikeStorageDao likeStorage;
    private final UserStorageDao userStorage;
    private final Film film1 = new Film(1, "Пираты Карибского моря: Проклятие Черной жемчужины",
            "Пират нападает на армию мертвецов, чтобы вернуть свой корабль. " +
                    "Боевик о первых приключениях Джека Воробья",
            LocalDate.of(2003, 8, 22),
            143, new MpaRating(3, "PG-13"),
            new HashSet<>());
    private final Film film2 = new Film(2, "Побег из Шоушенка",
            "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. " +
                    "Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием",
            LocalDate.of(1994, 9, 10), 142,
            new MpaRating(4, "R"),
            new HashSet<>());


    @Test
    void getAllIfEmptyFilmsTest() {
        assertEquals(Collections.EMPTY_LIST, new ArrayList<>(filmStorage.findAll()));
    }

    @Test
    void createFilmTest() {
        Film testFilm = filmStorage.create(film1);

        assertEquals(testFilm, filmStorage.findById(testFilm.getId()).get());
    }

    @Test
    void findFilmByIdTest() {
        filmStorage.create(film1);

        assertThat(filmStorage.findById(1))
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name",
                                "Пираты Карибского моря: Проклятие Черной жемчужины")
                );
    }

    @Test
    void getAllFilmTest() {
        filmStorage.create(film1);
        filmStorage.create(film2);

        assertEquals(List.of(film1, film2), filmStorage.findAll());
    }

    @Test
    void updateFilmTest() {
        filmStorage.create(film1);
        String newDescription = "Приключенческий фильм о пиратах, действие которого разворачивается " +
                "на Карибах в первой половине XVIII века.";
        Film updatedFilm = new Film(1, "Пираты Карибского моря: Проклятие Черной жемчужины",
                newDescription,
                LocalDate.of(2003, 8, 22),
                143, new MpaRating(3, "PG-13"),
                new HashSet<>());
        filmStorage.updateFilm(updatedFilm);

        assertThat(filmStorage.findById(1))
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("description",
                                newDescription));
    }

    @Test
    void deleteFilmTest() {
        filmStorage.create(film1);
        filmStorage.create(film2);
        filmStorage.deleteFilm(film1.getId());

        assertEquals(List.of(film2), filmStorage.findAll());
    }

    @Test
    void getPopularFilmTest() {
        final User user1 = new User(1, "Vadim", "szfo", "vadimfaustov@yandex.ru",
                LocalDate.of(1990, 8, 26));

        final User user2 = new User(2, "Alexey", "anonymous", "anonymous@yandex.ru",
                LocalDate.of(1987, 4, 12));

        userStorage.create(user1);
        userStorage.create(user2);
        filmStorage.create(film1);
        filmStorage.create(film2);
        likeStorage.addLike(film2.getId(), user1.getId());
        likeStorage.addLike(film2.getId(), user2.getId());

        assertEquals(List.of(film2), filmStorage.getPopular(1));
    }
}