package de.pls.stundenplaner.scheduler.model;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public class Break extends TimeStamp {

    public Break(LocalTime startTime, LocalTime endTime) {
        super(startTime, endTime);
    }

}