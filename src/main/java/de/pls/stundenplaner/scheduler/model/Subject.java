package de.pls.stundenplaner.scheduler.model;

public enum Subject {

    MATH("Math"),
    GERMAN("German");

    private String name;

    Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}