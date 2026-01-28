package de.pls.stundenplaner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an assignment belonging to a specific user.
 */
@SuppressWarnings("all")
@Entity
@Table(name = "assignments")
@Getter @Setter @NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @NotNull private UUID userUUID;
    @NotNull private LocalDate dueDate;
    @NotNull private boolean completed;
    @NotNull private String notes;

    public Assignment(
           Subject subject,
           LocalDate dueDate,
           String notes
    ) {
        this.subject = subject;
        this.dueDate = dueDate;
        this.notes = notes;
        this.completed = false;
    }

}