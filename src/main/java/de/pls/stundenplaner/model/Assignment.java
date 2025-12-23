package de.pls.stundenplaner.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UUID userUUID;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    public Assignment() {}

    public Assignment(
            UUID userUUID,
            Subject subject,
            LocalDate dueDate
    ) {

        this.userUUID = userUUID;
        this.subject = subject;
        this.dueDate = dueDate;

    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
