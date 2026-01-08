package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.scheduler.model.TimeStamp;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.util.exceptions.auth.InvalidSessionException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchedulerService {

    private final SchedulerRepository schedulerRepository;
    private final UserRepository userRepository;

    public SchedulerService(SchedulerRepository schedulerRepository, UserRepository userRepository) {
        this.schedulerRepository = schedulerRepository;
        this.userRepository = userRepository;
    }

    // GET ALL
    public ResponseEntity<List<ScheduleDay>> getAllScheduleDays(@NotNull final UUID sessionID) {
        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        List<ScheduleDay> scheduleDays = schedulerRepository.findByUserUUID(user.getUserUUID());

        return new ResponseEntity<>(scheduleDays, HttpStatus.OK);
    }

    // GET
    public ResponseEntity<ScheduleDay> getScheduleDay(
            @NotNull final UUID sessionID,
            @NotNull final DayOfWeek day
    ) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        Optional<ScheduleDay> scheduleDay = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), day);

        return scheduleDay.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<ScheduleDay> updateSchedule(
            @NotNull final UUID sessionID,
            @NotNull final DayOfWeek dayOfWeek,
            @Valid final ScheduleDay updatedSchedule
    ) {

        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        // If Schedule doesn't exist -> create a new one
        ScheduleDay scheduleDay = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), dayOfWeek)
                .orElseGet(() -> {
                    ScheduleDay newDay = new ScheduleDay(dayOfWeek, user.getUserUUID());
                    schedulerRepository.save(newDay);
                    return newDay;
                });

        // Reload the TimeStamps
        scheduleDay.getTimeStamps().forEach(ts -> ts.setScheduleDay(null));
        scheduleDay.getTimeStamps().clear();

        List<TimeStamp> newTimestamps = new ArrayList<>();
        if (updatedSchedule.getTimeStamps() != null) {
            for (TimeStamp ts : updatedSchedule.getTimeStamps()) {
                ts.setScheduleDay(scheduleDay);
                newTimestamps.add(ts);
            }
        }
        scheduleDay.getTimeStamps().addAll(newTimestamps);

        schedulerRepository.save(scheduleDay);

        return new ResponseEntity<>(scheduleDay, HttpStatus.OK);
    }

}