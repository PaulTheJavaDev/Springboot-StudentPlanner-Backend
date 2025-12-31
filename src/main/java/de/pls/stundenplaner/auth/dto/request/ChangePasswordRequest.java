package de.pls.stundenplaner.auth.dto.request;

import de.pls.stundenplaner.util.PasswordHasher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("all")
public class ChangePasswordRequest {

    @NotBlank
    @Size(min = 6, message = "Password cannot have less than 6 characters!")
    String password;

    @NotBlank
    @Size(min = 6, message = "Password cannot have less than 6 characters!")
    String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
