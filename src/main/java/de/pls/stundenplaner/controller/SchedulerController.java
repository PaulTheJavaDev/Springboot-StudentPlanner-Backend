package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.request.scheduler.CreateTimeStampRequest;
import de.pls.stundenplaner.dto.request.scheduler.UpdateTimeStampRequest;
import de.pls.stundenplaner.model.DayOfWeek;
import de.pls.stundenplaner.model.ScheduleDay;
import de.pls.stundenplaner.model.TimeStamp;
import de.pls.stundenplaner.service.SchedulerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controls the flow from the Lessons and Breaks to the Frontend
 */
@RestController
@RequestMapping("/schedule/me")
public class SchedulerController {

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDay>> getSchedule(
            final @NotNull @RequestHeader("SessionID") UUID sessionID
    ) {
        List<ScheduleDay> scheduleDays = schedulerService.getAllScheduleDays(sessionID);
        return ResponseEntity.ok(scheduleDays);
    }

    @PostMapping("/{dayOfWeek}")
    public ResponseEntity<TimeStamp> createTimeStamp(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody CreateTimeStampRequest createTimeStampRequest
    ) {
        TimeStamp timeStamp = schedulerService.createTimeStamp(sessionID, dayOfWeek, createTimeStampRequest);
        return ResponseEntity.ok(timeStamp);
    }

    @PutMapping("/{dayOfWeek}/{timeStampId}")
    public ResponseEntity<TimeStamp> updateTimeStamp(
            final @NotNull @RequestHeader("SessionID") UUID sessionID,
            final @NotNull @PathVariable DayOfWeek dayOfWeek,
            final @PathVariable int timeStampId,
            @RequestBody UpdateTimeStampRequest updateTimeStampRequest
    ) {

        TimeStamp timeStamp = schedulerService.updateTimeStamp(
                sessionID,
                dayOfWeek,
                timeStampId,
                updateTimeStampRequest
        );
        return ResponseEntity.ok(timeStamp);

    }

    @DeleteMapping("/{dayOfWeek}/{timeStampId}")
    public ResponseEntity<Void> deleteTimeStamp(
            final @PathVariable int timeStampId,
            final @NotNull @PathVariable DayOfWeek dayOfWeek,
            final @NotNull @RequestHeader UUID sessionID
    ) {
        schedulerService.deleteTimeStamp(sessionID, dayOfWeek, timeStampId);
        return ResponseEntity.ok().build();
    }

}