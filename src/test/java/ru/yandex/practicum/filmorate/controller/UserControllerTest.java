package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    UserController uc = new UserController();

    @DisplayName("Проверка валидации пользователя")
    @Test
    void shouldThrowValidateException() {
        User user = User.builder()
                .email("ros.kh.23@yandex.ru")
                .login("Lev7ne")
                .name("Rostislav")
                .birthday(LocalDate.of(2993, 11, 02))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> uc.createUser(user));
        assertEquals("Попытка добавить пользователя с датой рождения в будущем: " + user.getBirthday(), exception.getMessage());
    }

    @DisplayName("Проверка подмены имени логином")
    @Test
    void shouldChangeLoginWithoutName() {
        User user = User.builder()
                .email("ros.kh.23@yandex.ru")
                .login("Lev7ne")
                .birthday(LocalDate.of(1993, 11, 02))
                .build();
        uc.createUser(user);
        assertTrue(uc.getUsers().contains(user));
        User anyUser = uc.getUsers()
                .stream()
                .filter(user1 -> Objects.equals(user1.getEmail(), "ros.kh.23@yandex.ru"))
                .findFirst()
                .orElse(null);
        assertEquals(anyUser.getName(), anyUser.getLogin());
    }
}
