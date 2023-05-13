package ru.yandex.practicum.filmorate.model;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Slf4j
public class Validator {
    private static final LocalDate birthdayCinema = LocalDate.of(1895, 12, 28);

    public void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(birthdayCinema)) {
            log.warn("Добавить фильм выпущенный до 28.12.1895 - невозможно.");
            throw new ValidationException("Дата релиза фильма " + film.getReleaseDate() + " раньше даты рождения кинематографа.");
        }
    }
}
