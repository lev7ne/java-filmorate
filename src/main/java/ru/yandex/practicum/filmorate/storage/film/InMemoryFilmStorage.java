package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Counter counter = new Counter();
    private final Map<Integer, Film> films = new HashMap<>();
    private final Validator validator;

    @Autowired
    public InMemoryFilmStorage(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Film createFilm(Film film) {
        validator.validateFilm(film);
        int id = counter.getId();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        validator.validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(Integer id) {
        Film anyFilm = films.get(id);
        if (anyFilm == null) {
            log.warn("Фильм с id {} не найден.", id);
            throw new NotFoundException();
        }
        return anyFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }
}
