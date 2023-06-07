package ru.yandex.practicum.filmorate.dao;

import java.util.Collection;

public interface Dao<T> {
    T create(T entity);

    T update(T entity);

    T getById(Integer id);

    Collection<T> getAll();
}
