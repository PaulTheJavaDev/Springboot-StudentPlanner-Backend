package de.pls.stundenplaner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a TimeStamp in the Scheduler belonging to a specific user.
 */
@SuppressWarnings("all")
@Entity
@Getter @Setter @NoArgsConstructor
public class TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull private String type;
    @NotNull private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_day_id")
    @JsonBackReference
    private ScheduleDay scheduleDay;

    public TimeStamp(String type) {
        this.type = type;
        this.text = (type.equalsIgnoreCase("Lesson")) ? "Lesson" : "Break";
    }

}