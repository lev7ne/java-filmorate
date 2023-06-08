package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Validator;

import java.util.Collection;

@Slf4j
@Service
public class FilmService {
    private final FilmDao filmDao;
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final Validator validator;

    @Autowired
    public FilmService(FilmDao filmDao, LikeDao likeDao, UserDao userDao, Validator validator) {
        this.filmDao = filmDao;
        this.likeDao = likeDao;
        this.userDao = userDao;
        this.validator = validator;
    }

    public Film createFilm(Film film) {
        validator.validateFilm(film);
        return filmDao.create(film);
    }

    public Film updateFilm(Film film) {
        validator.validateFilm(film);
        return filmDao.update(film);
    }

    public Film getFilmById(Integer id) {
        return filmDao.getById(id);
    }

    public Collection<Film> getFilms() {
        return filmDao.getAll();
    }

    public void addLike(Integer filmId, Integer userId) {
        likeDao.add(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        userDao.getById(userId);
        likeDao.delete(filmId, userId);
    }

    public Collection<Film> getPopular(int count) {
        return filmDao.getPopular(count);
    }
}
