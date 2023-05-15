package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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
        Film anyFilm = getFilmById(filmId);
        userStorage.getUserById(userId);
        anyFilm.getLikes().add(userId);
        return filmStorage.updateFilm(anyFilm);
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        Film anyFilm = getFilmById(filmId);
        userStorage.getUserById(userId);
        anyFilm.getLikes().remove(userId);
        return filmStorage.updateFilm(anyFilm);
    }

    public List<Film> returnMostLikedFilms(int count) {
        return getFilms().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
