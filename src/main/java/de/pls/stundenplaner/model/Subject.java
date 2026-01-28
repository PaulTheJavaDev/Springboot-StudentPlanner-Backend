package de.pls.stundenplaner.model;

import lombok.Getter;

@SuppressWarnings("all")
@Getter
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

}