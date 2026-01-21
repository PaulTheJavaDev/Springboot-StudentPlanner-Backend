package de.pls.stundenplaner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(name = "assignments")
@Getter @Setter @NoArgsConstructor
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @NonNull private UUID userUUID;
    private LocalDate dueDate;
    private boolean completed;
    private String notes;

    public AssignmentEntity(
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
