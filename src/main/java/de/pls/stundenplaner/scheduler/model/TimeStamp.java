package de.pls.stundenplaner.scheduler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type; // "lesson" oder "break"
    private String text; // "Math" oder "Break"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_day_id")
    @JsonBackReference
    private ScheduleDay scheduleDay;

    public TimeStamp() {
    }

    public TimeStamp(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ScheduleDay getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(ScheduleDay scheduleDay) {
        this.scheduleDay = scheduleDay;
    }
}
