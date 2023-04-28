package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.LocalDate;

@lombok.Data
public class Film {

//    целочисленный идентификатор — id;
//    название — name;
//    описание — description;
//    дата релиза — releaseDate;
//    продолжительность фильма — duration.

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}
