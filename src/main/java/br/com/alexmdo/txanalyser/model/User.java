package br.com.alexmdo.txanalyser.model;

import br.com.alexmdo.txanalyser.controller.dto.UserDto;
import br.com.alexmdo.txanalyser.controller.form.NewUserForm;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Setter
    private String username;
    @Setter
    private String password;
    private Boolean enabled;
    @Setter
    private String name;

    public static List<UserDto> toDto(List<User> users) {
        return users.stream().map(user -> new UserDto(user.getId(), user.getName(), user.getUsername())).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public NewUserForm toForm() {
        return new NewUserForm(this.id, this.getName(), this.getUsername());
    }
}