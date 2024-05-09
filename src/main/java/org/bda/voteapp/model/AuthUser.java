package org.bda.voteapp.model;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
