package ru.yandex.practicum.filmorate.model;

import java.time.Instant;

@lombok.Data
public class User {

//    целочисленный идентификатор — id;
//    электронная почта — email;
//    логин пользователя — login;
//    имя для отображения — name;
//    дата рождения — birthday.

    int id;
    String email;
    String login;
    String name;
    Instant birthday;
}
