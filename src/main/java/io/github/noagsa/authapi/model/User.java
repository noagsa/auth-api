package io.github.noagsa.authapi.model;

public class User {
    private long id;
    private String email;
    private String password;
    private Role role;

    public User(Role role, String password, String email, long id) {
        this.role = role;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
