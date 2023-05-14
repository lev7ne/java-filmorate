package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Validator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;
    Validator validate = new Validator();
    Counter counter = new Counter();

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film createFilm(Film film) {
        validate.validateFilm(film);
        int id = counter.getId();
        film.setId(id);
        return filmStorage.saveFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.getFilmById(film.getId()) == null) {
            log.warn("Попытка обновить фильм с несуществующим id.");
            throw new NotFoundException();
        }
        validate.validateFilm(film);
        filmStorage.saveFilm(film);
        return film;
    }

    public Film getFilmById(Integer id) {
        if (filmStorage.getFilmById(id) == null) {
            throw new NotFoundException();
        }
        return filmStorage.getFilmById(id);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film anyFilm = filmStorage.getFilmById(filmId);
        User anyUser = userStorage.getUserById(userId);
        if (anyFilm == null || anyUser == null) {
            log.warn("Попытка поставить лайк фильму с несуществующим id или пользователь с таким id не существует.");
            throw new NotFoundException();
        }
        anyFilm.getLikes().add(userId);
        filmStorage.saveFilm(anyFilm);
        return anyFilm;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        Film anyFilm = filmStorage.getFilmById(filmId);
        User anyUser = userStorage.getUserById(userId);
        if (anyFilm == null || anyUser == null) {
            log.warn("Попытка удалить лайк фильму с несуществующим id или пользователь с таким id не существует.");
            throw new NotFoundException();
        }
        anyFilm.getLikes().remove(userId);
        filmStorage.saveFilm(anyFilm);
        return anyFilm;
    }

    public List<Film> returnMostLikedFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmStorage.getFilms().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
