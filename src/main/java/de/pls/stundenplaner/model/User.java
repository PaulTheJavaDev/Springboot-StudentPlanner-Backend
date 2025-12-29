package de.pls.stundenplaner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@SuppressWarnings("all")
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

    @Column(unique = true)
    @JsonProperty("sessionID")
    private UUID sessionID;

    protected User() {
    }

    public User(
            @NonNull String username,
            @NonNull String password_hash
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

    public void setSessionID(UUID sessionID) {
        this.sessionID = sessionID;
    }

    public UUID getSessionID() {
        return sessionID;
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