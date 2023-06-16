package ru.yandex.practicum.filmorate.dao;

public interface Addable<T> {
    void add(Integer id1, Integer id2);

    void delete(Integer id1, Integer id2);
}
