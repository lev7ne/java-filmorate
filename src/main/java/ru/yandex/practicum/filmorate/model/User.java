package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonProperty("name")
    private String userName;
    @NotBlank
    @NotNull
    private String login;
    @Email
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

}
