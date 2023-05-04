package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final Counter counter = new Counter();

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validate(user);
        int id = counter.getId();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Попытка обновить пользователя с несуществующим id.");
            throw new ValidationException();
        }
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Попытка добавить пользователя с некорректным логином.");
            throw new ValidationException();
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Попытка добавить пользователя с датой рождения в будущем.");
            throw new ValidationException();
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
