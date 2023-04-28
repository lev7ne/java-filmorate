package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@lombok.Data
public class User {

//    целочисленный идентификатор — id;
//    электронная почта — email;
//    логин пользователя — login;
//    имя для отображения — name;
//    дата рождения — birthday.

    private Integer id;
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String login;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
}
