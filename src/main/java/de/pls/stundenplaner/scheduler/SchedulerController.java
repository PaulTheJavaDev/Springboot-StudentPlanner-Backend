package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.scheduler.model.TimeStamp;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
            final @NotNull @RequestHeader String sessionID
    ) {
        return schedulerService.getAllScheduleDays(sessionID);
    }

    @PostMapping("/{dayOfWeek}")
    public ResponseEntity<TimeStamp> createTimeStamp(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody Map<String, String> body
    ) {
        String type = body.get("type");
        return schedulerService.createTimeStamp(sessionID, dayOfWeek, type);
    }

    @PutMapping("/{dayOfWeek}/{timeStampId}")
    public ResponseEntity<TimeStamp> updateTimeStamp(
            final @NotNull @RequestHeader("SessionID") UUID sessionID,
            final @NotNull @PathVariable DayOfWeek dayOfWeek,
            final @PathVariable int timeStampId,
            final @NotNull @Valid @RequestBody Map<String, String> body
    ) {
        return schedulerService.updateTimeStamp(
                sessionID,
                dayOfWeek,
                timeStampId,
                body
        );
    }

    @DeleteMapping("/{dayOfWeek}/{timeStampId}")
    public ResponseEntity<Void> deleteTimeStamp(
            final @PathVariable int timeStampId,
            final @NotNull @PathVariable DayOfWeek dayOfWeek,
            final @NotNull @RequestHeader UUID sessionID
    ) {
        return schedulerService.deleteTimeStamp(sessionID, dayOfWeek, timeStampId);
    }

}