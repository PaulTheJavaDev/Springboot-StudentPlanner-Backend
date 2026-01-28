package de.pls.stundenplaner.controller;

import de.pls.stundenplaner.dto.request.scheduler.CreateTimeStampRequest;
import de.pls.stundenplaner.dto.request.scheduler.UpdateTimeStampRequest;
import de.pls.stundenplaner.model.DayOfWeek;
import de.pls.stundenplaner.model.ScheduleDay;
import de.pls.stundenplaner.model.TimeStamp;
import de.pls.stundenplaner.service.TimeStampService;
import de.pls.stundenplaner.util.exceptions.InvalidSessionException;
import de.pls.stundenplaner.util.exceptions.UnauthorizedAccessException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Handles Web Requests for the Scheduler via {@link TimeStampService}
 */
@RestController
@RequestMapping("/schedule/me")
public class TimeStampController {

    private final TimeStampService timeStampService;

    public TimeStampController(TimeStampService timeStampService) {
        this.timeStampService = timeStampService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDay>> getSchedule(
            final @NotNull @RequestHeader("SessionID") UUID sessionID
    ) throws InvalidSessionException {

        List<ScheduleDay> scheduleDays = timeStampService.getAllScheduleDays(sessionID);
        return ResponseEntity.ok(scheduleDays);

    }

    @PostMapping("/{dayOfWeek}")
    public ResponseEntity<TimeStamp> createTimeStamp(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody CreateTimeStampRequest createTimeStampRequest
    ) throws InvalidSessionException {
        TimeStamp timeStamp = timeStampService.createTimeStamp(sessionID, dayOfWeek, createTimeStampRequest);
        return ResponseEntity.ok(timeStamp);
    }

    @PutMapping("/{dayOfWeek}/{timeStampId}")
    public ResponseEntity<TimeStamp> updateTimeStamp(
            @NotNull @RequestHeader("SessionID") final UUID sessionID,
            @NotNull @PathVariable final DayOfWeek dayOfWeek,
            @PathVariable int timeStampId,
            @NotNull @RequestBody @Valid final UpdateTimeStampRequest updateTimeStampRequest
    ) throws InvalidSessionException, UnauthorizedAccessException {

        TimeStamp timeStamp = timeStampService.updateTimeStamp(
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
    ) throws InvalidSessionException, UnauthorizedAccessException {

        timeStampService.deleteTimeStamp(sessionID, dayOfWeek, timeStampId);
        return ResponseEntity.ok().build();

    }

}