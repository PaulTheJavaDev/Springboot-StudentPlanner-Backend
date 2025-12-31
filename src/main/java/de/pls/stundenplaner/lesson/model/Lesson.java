package de.pls.stundenplaner.lesson.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Subject subject;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID userUUID;

    public Lesson(Subject subject, LocalTime startTime, LocalTime endTime) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lesson() {
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
