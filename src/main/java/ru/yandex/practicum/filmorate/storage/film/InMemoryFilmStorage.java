package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Validator;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Validator validate = new Validator();
    private final Counter counter = new Counter();
    private final UserStorage userStorage;

    @Autowired
    public InMemoryFilmStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Film createFilm(Film film) {
        validate.validateFilm(film);
        int id = counter.getId();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        validate.validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(Integer id) {
        Film anyFilm = films.get(id);
        if (anyFilm == null) {
            log.warn("Фильм с id {} не найден.", id);
            throw new NotFoundException();
        }
        return anyFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film addLike(Integer filmId, Integer userId) {
        Film anyFilm = getFilmById(filmId);
        userStorage.getUserById(userId);
        anyFilm.getLikes().add(userId);
        films.put(anyFilm.getId(), anyFilm);
        return anyFilm;
    }

    @Override
    public Film deleteLike(Integer filmId, Integer userId) {
        Film anyFilm = getFilmById(filmId);
        userStorage.getUserById(userId);
        anyFilm.getLikes().remove(userId);
        films.put(anyFilm.getId(), anyFilm);
        return anyFilm;
    }

    @Override
    public List<Film> returnMostLikedFilms(int count) {
        return getFilms().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
