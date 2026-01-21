package de.pls.stundenplaner.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"userUUID", "dayOfWeek"})
)
public class ScheduleDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private UUID userUUID;

    @OneToMany(
            mappedBy = "scheduleDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TimeStamp> timeStamps = new ArrayList<>();

    protected ScheduleDay() {
    }

    public ScheduleDay(DayOfWeek dayOfWeek, UUID userUUID) {
        this.dayOfWeek = dayOfWeek;
        this.userUUID = userUUID;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public List<TimeStamp> getTimeStamps() {
        return timeStamps;
    }
}
