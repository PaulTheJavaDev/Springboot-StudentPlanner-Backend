package de.pls.stundenplaner.scheduler.model;

public class Lesson extends TimeStamp {

    private Subject subject;

    public Lesson(Subject subject) {
        super("lesson", subject.getName());
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
