package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.MpaRatingStorageDao;

import java.util.List;

@Slf4j
@Service
public class MpaRatingServiceImpl implements MpaRatingService {
    private final MpaRatingStorageDao mpaRatingDao;

    @Autowired
    public MpaRatingServiceImpl(MpaRatingStorageDao mpaRatingDao) {
        this.mpaRatingDao = mpaRatingDao;
    }

    @Override
    public List<MpaRating> findAll() {
        return mpaRatingDao.findAll();
    }

    @Override
    public MpaRating findById(int id) {
        return mpaRatingDao.findById(id)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска рейтинга фильма по id");
                    throw new NotFoundException(HttpStatus.NOT_FOUND,
                            String.format("Рейтинг с id = %s не найден.", id));
                });
    }
}