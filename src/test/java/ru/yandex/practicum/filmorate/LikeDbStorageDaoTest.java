package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LikeDbStorageDaoTest {
    private final LikeStorageDao likeStorage;
    private final UserStorageDao userStorage;
    private final FilmStorageDao filmStorage;
    private final User user1 = new User(1, "Vadim", "szfo", "vadimfaustov@yandex.ru",
            LocalDate.of(1990, 8, 26));
    private final Film film1 = new Film(1, "Пираты Карибского моря: Проклятие Черной жемчужины",
            "Пират нападает на армию мертвецов, чтобы вернуть свой корабль. " +
                    "Боевик о первых приключениях Джека Воробья",
            LocalDate.of(2003, 8, 22),
            143, new MpaRating(3, "PG-13"),
            new HashSet<>());

    @BeforeEach
    public void createUserAndFilmWithLike() {
        userStorage.create(user1);
        filmStorage.create(film1);
        likeStorage.addLike(film1.getId(), user1.getId());
    }

    @Test
    void addLike() {
        assertEquals(1, likeStorage.getLikes(film1.getId(), user1.getId()).size());
    }

    @Test
    void deleteLike() {
        likeStorage.deleteLike(film1.getId(), user1.getId());

        assertTrue(likeStorage.getLikes(film1.getId(), user1.getId()).isEmpty());
    }
}