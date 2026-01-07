package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.user.UserRepository;
import de.pls.stundenplaner.user.model.User;
import de.pls.stundenplaner.util.exceptions.auth.InvalidSessionException;
import de.pls.stundenplaner.util.exceptions.scheduler.InvalidSchedulerException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import java.util.List;
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

        ScheduleDay scheduleDay = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), day)
                .orElseThrow(InvalidSchedulerException::new);

        return new ResponseEntity<>(scheduleDay, HttpStatus.OK);
    }

    // UPDATE
    public ResponseEntity<ScheduleDay> updateSchedule(
            @NotNull final UUID sessionID,
            @NotNull final DayOfWeek dayOfWeek,
            @Valid final ScheduleDay updatedSchedule
    ) {
        User user = userRepository.findBySessionID(sessionID)
                .orElseThrow(InvalidSessionException::new);

        ScheduleDay scheduleDay = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), dayOfWeek)
                .orElseThrow(InvalidSchedulerException::new);

        scheduleDay.setTimeStamps(updatedSchedule.getTimeStamps());
        schedulerRepository.save(scheduleDay);

        return new ResponseEntity<>(scheduleDay, HttpStatus.OK);
    }
}