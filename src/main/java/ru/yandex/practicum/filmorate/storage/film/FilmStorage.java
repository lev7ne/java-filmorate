package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(Integer id);

    Collection<Film> getFilms();

    Film addLike(Integer filmId, Integer userId);

    Film deleteLike(Integer filmId, Integer userId);

    List<Film> returnMostLikedFilms(int count);
}
