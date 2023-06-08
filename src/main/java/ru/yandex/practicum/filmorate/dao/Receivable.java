package ru.yandex.practicum.filmorate.dao;

import java.util.Collection;

public interface Receivable<T> {
    T getById(Integer id);

    Collection<T> getAll();
}
