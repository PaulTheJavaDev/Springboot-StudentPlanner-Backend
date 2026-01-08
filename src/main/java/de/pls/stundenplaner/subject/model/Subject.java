package de.pls.stundenplaner.subject.model;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public enum Subject {

    MATH("Math"),
    GERMAN("German"),
    SCIENCE("Science"),
    POLITICS("Politics"),
    COMPUTER_SCIENCE("Computer Science");

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