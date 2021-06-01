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
    private AccessLevel accessLvl;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfModification;
}
