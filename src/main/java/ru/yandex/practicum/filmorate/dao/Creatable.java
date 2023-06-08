package ru.yandex.practicum.filmorate.dao;

public interface Creatable<T> {
    T create(T t);

    T update(T t);
}
