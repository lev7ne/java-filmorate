package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDao extends Receivable<Genre> {
    Genre getById(Integer id);

    Collection<Genre> getAll();
}