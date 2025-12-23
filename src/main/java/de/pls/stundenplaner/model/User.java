package de.pls.stundenplaner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, updatable = false)
    @JsonProperty("useruuid")
    private UUID userUUID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonProperty("password_hash")
    private String password_hash;

    protected User() {}

    public User(
            final @NotNull String username,
            final @NotNull String password_hash
    ) {
        this.username = username;
        this.password_hash = password_hash;
    }

    @PrePersist
    private void prePersist() {
        if (userUUID == null) {
            userUUID = UUID.randomUUID();
        }
    }

    public int getId() {
        return id;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword_hash(String passwordHash) {
        this.password_hash = passwordHash;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

}