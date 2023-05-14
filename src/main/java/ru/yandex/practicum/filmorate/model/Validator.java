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
    public void validateUser(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Попытка добавить пользователя с некорректным логином.");
            throw new ValidationException("Логин " + user.getLogin() + " - некорректный.");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Попытка добавить пользователя с датой рождения в будущем.");
            throw new ValidationException("Попытка добавить пользователя с датой рождения в будущем: " + user.getBirthday());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("Имя пользователя подставлено из логина: {}", user.getLogin());
            user.setName(user.getLogin());
        }
    }
}
