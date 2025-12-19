package de.pls.stundenplaner.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String studentUUID;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    public Assignment() {
    }

    public Assignment(int id, String studentUUID, Subject subject, LocalDate dueDate) {
        this.id = id;
        this.studentUUID = studentUUID;
        this.subject = subject;
        this.dueDate = dueDate;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentUUID(String identifier) {
        this.studentUUID = identifier;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getStudentUUID() {
        return studentUUID;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
