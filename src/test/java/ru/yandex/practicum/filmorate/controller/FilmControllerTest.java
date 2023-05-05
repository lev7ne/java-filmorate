package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    FilmController fc = new FilmController();

    @DisplayName("Проверка валидации фильма")
    @Test
    void shouldThrowValidateException() {
        Film film = Film.builder()
                .name("Титаник")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(993, 12, 4))
                .duration((short) 120)
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> fc.createFilm(film));
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
        fc.createFilm(film);
        assertEquals(fc.getFilms().size(), 1);
        assertTrue(fc.getFilms().contains(film));
    }
}
