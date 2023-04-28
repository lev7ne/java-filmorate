package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Set<Film> films = new HashSet<>();
    private final Counter counter = new Counter();

    /*
    Валидация:
    - название не может быть пустым;
    - максимальная длина описания — 200 символов;
    - дата релиза — не раньше;
    - продолжительность фильма должна быть положительной.
    */

    @PostMapping
    public Film createFilm(@RequestBody Film film) {

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {

        return film;
    }

    @GetMapping
    public Set<Film> getFilms() {

        return films;
    }
}
