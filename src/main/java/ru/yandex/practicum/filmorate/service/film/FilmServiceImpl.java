package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorageDao;
import ru.yandex.practicum.filmorate.storage.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.storage.film.GenreStorageDao;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDateValidator;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorageDao filmStorage;

    private final FilmReleaseDateValidator filmReleaseDateValidator;

    private final GenreStorageDao genreStorage;

    private final LikeStorageDao likeDbStorage;

    @Override
    public List<Film> getAll() {
        List<Film> films = filmStorage.getAll();

        return films.stream()
                .peek(a -> a.setGenres(genreStorage.load(a)))
                .collect(Collectors.toList());
    }

    @Override
    public Film create(Film film) {
        filmReleaseDateValidator.validate(film);
        filmStorage.create(film);
        genreStorage.set(film);
        film.setGenres(genreStorage.load(film));

        return getById(film.getId());
    }

    @Override
    public Film update(Film film) {
        filmReleaseDateValidator.validate(film);
        Film result = filmStorage.update(film)
                .orElseThrow(() -> new NotFoundException(String.format("Фильм с id = %s не найден.", film.getId())));
        genreStorage.set(film);
        film.setGenres(genreStorage.load(film));

        return result;
    }

    @Override
    public void delete(int id) {
        filmStorage.delete(id);
    }

    @Override
    public Film getById(int id) {
        Film result = filmStorage.getById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска фильма по id");
                    throw new NotFoundException(String.format("Фильм с ID %d не найден", id));
                });
        result.setGenres(genreStorage.load(result));

        return result;
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (!likeDbStorage.add(filmId, userId)) {
            log.warn("Ошибка добавления лайка к фильму по его id.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                            "ID = %s пользователя", filmId, userId));
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!likeDbStorage.delete(filmId, userId)) {
            log.warn("Ошибка удаления лайка у фильма по его id.");
            throw new NotFoundException(String.format("Проверьте корректность ввода ID = %s фильма и " +
                            "ID = %s пользователя", filmId, userId));
        }
    }

    @Override
    public List<Film> getPopular(int count) {
        return filmStorage.getPopular(count);
    }
}