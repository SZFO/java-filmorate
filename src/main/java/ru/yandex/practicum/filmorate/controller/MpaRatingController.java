package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.film.MpaRatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaRatingController {
    private final MpaRatingService mpaRatingService;

    @Autowired
    public MpaRatingController(MpaRatingService mpaRatingService) {
        this.mpaRatingService = mpaRatingService;
    }

    @GetMapping
    public List<MpaRating> getAll() {
        log.info("Вывод списка всех рейтингов.");
        return mpaRatingService.getAll();
    }

    @GetMapping("{id}")
    public MpaRating getById(@PathVariable int id) {
        log.info("Получение рейтинга по его ID.");
        return mpaRatingService.getById(id);
    }
}