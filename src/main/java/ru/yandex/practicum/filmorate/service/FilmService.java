package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilmById(id);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addLike(Integer filmId, Integer userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        return filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> returnMostLikedFilms(int count) {
        return filmStorage.returnMostLikedFilms(count);
    }
}
