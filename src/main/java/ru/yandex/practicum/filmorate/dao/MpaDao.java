package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDao extends Receivable<Mpa> {
    Mpa getById(Integer id);

    Collection<Mpa> getAll();
}