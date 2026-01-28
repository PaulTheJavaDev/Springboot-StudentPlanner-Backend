package de.pls.stundenplaner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull private Subject subject;
    @NotNull private UUID userUUID;
    @NotNull private String notes;
    @NotNull private LocalDate dueDate;

    public Exam(
            Subject subject,
            UUID userUUID,
            String notes,
            LocalDate dueDate
    ) {
        this.subject = subject;
        this.userUUID = userUUID;
        this.notes = notes;
        this.dueDate = dueDate;
    }

}