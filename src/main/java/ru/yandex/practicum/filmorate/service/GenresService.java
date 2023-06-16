package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public class GenresService {
    private final GenreDao genresDao;

    @Autowired
    public GenresService(GenreDao genresDao) {
        this.genresDao = genresDao;
    }

    public Genre getById(Integer id) {
        return genresDao.getById(id);
    }

    public Collection<Genre> getAll() {
        return genresDao.getAll();
    }
}
