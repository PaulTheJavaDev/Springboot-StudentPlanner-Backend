package de.pls.stundenplaner.scheduler.model;

import java.time.LocalTime;

public class Lesson extends TimeStamp {

    private Subject subject;

    public Lesson(LocalTime startTime, LocalTime endTime, Subject subject) {
        super(startTime, endTime);
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
