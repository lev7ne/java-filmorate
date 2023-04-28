package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.Instant;

@lombok.Data
public class Film {

//    целочисленный идентификатор — id;
//    название — name;
//    описание — description;
//    дата релиза — releaseDate;
//    продолжительность фильма — duration.

    int id;
    String name;
    String description;
    Instant releaseDate;
    Duration duration;
}
