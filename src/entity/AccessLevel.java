package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessLevel {
    private long id;
    private String title;

    @Override
    public String toString() {
        return title;
    }
}
