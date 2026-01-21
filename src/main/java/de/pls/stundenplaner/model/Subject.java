package de.pls.stundenplaner.model;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public enum Subject {

    MATH("Math"),
    GERMAN("German"),
    SCIENCE("Science"),
    POLITICS("Politics"),
    COMPUTER_SCIENCE("Computer Science"),
    ENGLISH("English"),
    HISTORY("History"),
    PHYSICAL_EDUCATION("Physical Education"),
    MUSIC("Music"),
    ART("Art"),
    RELIGION("Religion");

    private final String name;

    Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Subject> getAllSubjects() {
        return Arrays.stream(Subject.values()).toList();
    }
}