package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private long id;
    private String login;
    private String password;
    private long accessLvl;
    private LocalDateTime dateOfCreation;
    private LocalDate dateOfModification;
}
