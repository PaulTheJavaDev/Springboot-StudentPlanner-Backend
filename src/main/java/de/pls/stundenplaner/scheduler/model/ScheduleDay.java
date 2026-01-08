package de.pls.stundenplaner.scheduler.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ScheduleDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_day_id")
    @JsonManagedReference
    private List<TimeStamp> timeStamps = new ArrayList<>();

    private UUID userUUID;

    public ScheduleDay(DayOfWeek dayOfWeek, UUID userUUID) {
        this.dayOfWeek = dayOfWeek;
        this.userUUID = userUUID;
        this.timeStamps = new ArrayList<TimeStamp>();
    }

    public ScheduleDay() {
    }

    public int getId() {
        return id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public List<TimeStamp> getTimeStamps() {
        return timeStamps;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public void setTimeStamps(List<TimeStamp> timeStamps) {
        this.timeStamps = timeStamps;
    }
}