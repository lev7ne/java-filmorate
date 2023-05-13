package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Validator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    Validator validate = new Validator();
    Counter counter = new Counter();

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
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
            throw new ValidationException();
        }
        validate.validateFilm(film);
        filmStorage.saveFilm(film);
        return film;
    }
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film anyFilm = filmStorage.getFilmById(filmId);
        if (anyFilm == null) {
            log.warn("Попытка добавить лайк фильму с несуществующим id.");
            throw new ValidationException();
        }
        anyFilm.getLikes().add(userId);
        filmStorage.saveFilm(anyFilm);
        return anyFilm;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        Film anyFilm = filmStorage.getFilmById(filmId);
        if (anyFilm == null) {
            log.warn("Попытка удалить лайк фильму с несуществующим id.");
            throw new ValidationException();
        }
        anyFilm.getLikes().remove(userId);
        filmStorage.saveFilm(anyFilm);
        return anyFilm;
    }

    public List<Film> returnMostLikedFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        Set<Film> films = new TreeSet<>(
                new Comparator<Film>() {
                    @Override
                    public int compare(Film o1, Film o2) {
                        return Integer.compare(o2.getLikes().size(), o1.getLikes().size());
                    }
                }
        );
        films.addAll(filmStorage.getFilms());
        //collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingInt(Player::getNumberOfWins))));
        return films.stream().limit(count).collect(Collectors.toList());
    }
}
