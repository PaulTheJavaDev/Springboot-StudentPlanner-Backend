package de.pls.stundenplaner.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("all")
public abstract class AuthRequest {

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @Size(min = 6, message = "Password cannot have less than 6 characters!")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
