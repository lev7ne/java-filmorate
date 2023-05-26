package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private Integer id;
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String login;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();
}
