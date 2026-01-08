package de.pls.stundenplaner.assignment.model;

import de.pls.stundenplaner.subject.model.Subject;
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

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private UUID userUUID;
    private LocalDate dueDate;
    private boolean completed;
    private String notes;

    public Assignment() {
    }

    public Assignment(
            Subject subject,
            LocalDate dueDate,
            String notes
    ) {

        this.subject = subject;
        this.dueDate = dueDate;
        this.notes = notes;
        this.completed = false;

    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
