package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorageDao;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.film.GenreStorageDao;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDateValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorageDao filmStorage;
    private final FilmReleaseDateValidator filmReleaseDateValidator;
    private final GenreStorageDao genreStorage;
    private final LikeStorageDao likeDbStorage;

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film create(Film film) {
        filmReleaseDateValidator.validate(film);
        filmStorage.create(film);
        genreStorage.setFilmGenre(film);
        film.setGenres(genreStorage.loadFilmGenre(film));

        return findById(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        filmReleaseDateValidator.validate(film);
        Film result = filmStorage.updateFilm(film)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                        String.format("Фильм с id = %s не найден.", film.getId())));
        genreStorage.setFilmGenre(film);
        film.setGenres(genreStorage.loadFilmGenre(film));

        return result;
    }

    @Override
    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    @Override
    public Film findById(int id) {
        Film result = filmStorage.findById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска фильма по id");
                    throw new NotFoundException(HttpStatus.NOT_FOUND,
                            String.format("Фильм с ID %d не найден", id));
                });
        result.setGenres(genreStorage.loadFilmGenre(result));

        return result;
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (!likeDbStorage.addLike(filmId, userId)) {
            log.warn("Ошибка добавления лайка к фильму по его id.");
            throw new NotFoundException(HttpStatus.NOT_FOUND,
                    String.format("Проверьте корректность ввода ID = %s фильма и " +
                            "ID = %s пользователя", filmId, userId));
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!likeDbStorage.deleteLike(filmId, userId)) {
            log.warn("Ошибка удаления лайка у фильма по его id.");
            throw new NotFoundException(HttpStatus.NOT_FOUND,
                    String.format("Проверьте корректность ввода ID = %s фильма и " +
                            "ID = %s пользователя", filmId, userId));
        }
    }

    @Override
    public List<Film> getPopular(int count) {
        return filmStorage.getPopular(count);
    }
}