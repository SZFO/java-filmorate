package ru.yandex.practicum.filmorate.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.MpaRatingStorageDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MpaRatingDbStorageDaoTest {
    private final MpaRatingStorageDao mpaRatingStorage;

    private final List<MpaRating> ratings = List.of(new MpaRating(1, "G"),
            new MpaRating(2, "PG"),
            new MpaRating(3, "PG-13"),
            new MpaRating(4, "R"),
            new MpaRating(5, "NC-17"));

    @Test
    void getAllMpaRatingTest() {
        assertEquals(ratings, mpaRatingStorage.getAll());
    }

    @Test
    void findMpaRatingByIdTest() {
        assertThat(mpaRatingStorage.getById(3))
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("name", "PG-13")
                );
    }
}