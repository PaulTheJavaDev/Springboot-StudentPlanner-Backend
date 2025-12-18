package de.pls.stundenplaner.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private UUID studentUUID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Column(nullable = false)
    private Date dueDate;

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentUUID(UUID studentUUID) {
        this.studentUUID = studentUUID;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public UUID getStudentUUID() {
        return studentUUID;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
