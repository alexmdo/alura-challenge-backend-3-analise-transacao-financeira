package br.com.alexmdo.txanalyser.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authorities")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority {

    @Id
    @Setter
    private String username;
    private String authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(username, authority.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}