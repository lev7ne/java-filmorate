package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film saveFilm(Film film);
    Collection<Film> getFilms();
    Film getFilmById(Integer id);
}