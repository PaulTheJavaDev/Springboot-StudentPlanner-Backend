package de.pls.stundenplaner.exam.model;

import de.pls.stundenplaner.lesson.model.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private UUID userUUID;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private LocalDate dueDate;

    public Exam(
            final @NotNull Subject subject,
            final @NotNull String notes,
            final @NotNull LocalDate dueDate
    ) {
        this.subject = subject;
        this.notes = notes;
        this.dueDate = dueDate;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    protected Exam() {
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
