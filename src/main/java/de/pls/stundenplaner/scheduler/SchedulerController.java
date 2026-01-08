package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule/me")
public class SchedulerController {

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDay>> getAllSchedules(
            @RequestHeader("SessionID") UUID sessionID
    ) {
        return schedulerService.getAllScheduleDays(sessionID);
    }

    @GetMapping("/{dayOfWeek}")
    public ResponseEntity<ScheduleDay> getSchedule(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable DayOfWeek dayOfWeek
    ) {
        return schedulerService.getScheduleDay(sessionID, dayOfWeek);
    }

    @PutMapping("/{dayOfWeek}")
    public ResponseEntity<ScheduleDay> updateSchedule(
            @RequestHeader("SessionID") UUID sessionID,
            @PathVariable DayOfWeek dayOfWeek,
            @RequestBody @Valid ScheduleDay scheduleDay
    ) {
        return schedulerService.updateSchedule(sessionID, dayOfWeek, scheduleDay);
    }
}