package model;

import java.util.Set;

public class User extends AbstractNameEntity {
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Vote> votes;
}
