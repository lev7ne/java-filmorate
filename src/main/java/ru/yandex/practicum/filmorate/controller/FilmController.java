package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Counter counter = new Counter();

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        validate(film);
        int id = counter.getId();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Попытка обновить фильм с несуществующим id.");
            throw new ValidationException();
        }
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    private void validate(Film film) {
        if (film.getDescription().length() >= 200) {
            log.warn("Длина описания более 200 символов.");
            throw new ValidationException();
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Добавить фильм выпущенный до 28.12.1895 - невозможно.");
            throw new ValidationException();
        }
        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма не может быть отрицательной.");
            throw new ValidationException();
        }
    }
}
