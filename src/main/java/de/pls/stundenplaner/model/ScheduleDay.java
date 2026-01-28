package de.pls.stundenplaner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a ScheduleDay belonging to a specific user.
 */
@SuppressWarnings("all")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userUUID", "dayOfWeek"})) // Both need to be unique (from other values from these Fields), a user cannot have two TuesDays in the Database.
@Getter @Setter @NoArgsConstructor
public class ScheduleDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull private DayOfWeek dayOfWeek;
    @NotNull private UUID userUUID;
    @OneToMany(
            mappedBy = "scheduleDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TimeStamp> timeStamps = new ArrayList<>();

    public ScheduleDay(
            DayOfWeek dayOfWeek,
            UUID userUUID
    ) {
        this.dayOfWeek = dayOfWeek;
        this.userUUID = userUUID;
    }

}