package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    UserStorage userStorage = new InMemoryUserStorage();
    FilmStorage filmStorage = new InMemoryFilmStorage(userStorage);
    FilmService filmService = new FilmService(filmStorage);
    FilmController filmController = new FilmController(filmService);

    @DisplayName("Проверка валидации фильма")
    @Test
    void shouldThrowValidateException() {
        Film film = Film.builder()
                .name("Титаник")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(993, 12, 4))
                .duration((short) 120)
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals("Дата релиза фильма " + film.getReleaseDate() + " раньше даты рождения кинематографа.", exception.getMessage());
    }

    @DisplayName("Добавления фильма в Collection")
    @Test
    void shouldSaveFilm() {
        Film film = Film.builder()
                .name("Титаник")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(1993, 12, 4))
                .duration((short) 180)
                .build();
        filmController.createFilm(film);
        assertEquals(filmController.getFilms().size(), 1);
        assertTrue(filmController.getFilms().contains(film));
    }
}
