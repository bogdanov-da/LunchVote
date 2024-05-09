package org.bda.voteapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractNameEntity {
    @Setter
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank
    @Size(message = "Max length is 128 symbols", max = 128)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(message = "Password should has min 5 and max 10 symbols", min = 5, max = 128)
    @JsonIgnore
    private String password;

    @Setter
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDateTime registered = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    public User(Integer id, String name, String email, String password, LocalDateTime registered, Role... roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
        setRoles(List.of(roles));
    }

    public User(String name, String email, String password, Role... roles) {
        super(null, name);
        this.email = email;
        this.password = password;
        setRoles(List.of(roles));
    }

    public User(String name, String email, String password, Collection<Role> roles) {
        super(null, name);
        this.email = email;
        this.password = password;
        setRoles(roles);
    }

    public User(Integer id, String name, String email, String password, LocalDateTime registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                ", roles=" + roles +
                ", id=" + id +
                '}';
    }
}
