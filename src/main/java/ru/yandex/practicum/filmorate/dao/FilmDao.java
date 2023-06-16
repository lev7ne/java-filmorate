package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDao extends Creatable<Film>, Receivable<Film> {
    Collection<Film> getPopular(int count);
}