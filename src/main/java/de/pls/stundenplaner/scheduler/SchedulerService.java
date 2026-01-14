package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.DayOfWeek;
import de.pls.stundenplaner.scheduler.model.ScheduleDay;
import de.pls.stundenplaner.scheduler.model.TimeStamp;
import de.pls.stundenplaner.user.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

@Service
public class SchedulerService {

    private final TimeStampRepository timeStampRepository;
    private final SchedulerRepository schedulerRepository;

    public SchedulerService(TimeStampRepository timeStampRepository, SchedulerRepository schedulerRepository) {
        this.timeStampRepository = timeStampRepository;
        this.schedulerRepository = schedulerRepository;
    }

    public ResponseEntity<TimeStamp> updateTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            final int timeStampId,
            final @NotNull Map<String, String> body
    ) {
        TimeStamp timeStamp = getTimeStamp(
                sessionID,
                timeStampId,
                dayOfWeek
        ).getBody();

        assert timeStamp != null;

        if (body.containsKey("type")) {
            timeStamp.setType(body.get("type"));
        }

        timeStampRepository.save(timeStamp);

        return new ResponseEntity<>(timeStamp, HttpStatus.OK);
    }

    public ResponseEntity<TimeStamp> getTimeStamp(
            final @NotNull UUID sessionID,
            final int timeStampId,
            final @NotNull DayOfWeek dayOfWeek
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        TimeStamp timeStamp = timeStampRepository.findById(timeStampId).orElseThrow();

        ScheduleDay day = timeStamp.getScheduleDay();

        if (!day.getUserUUID().equals(user.getUserUUID())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (day.getDayOfWeek() != dayOfWeek) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(timeStamp, HttpStatus.OK);
    }

    // Create new timestamp
    public ResponseEntity<TimeStamp> createTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            final @NotNull String type
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        // Find or create ScheduleDay lazily
        ScheduleDay day = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), dayOfWeek)
                .orElseGet(() -> schedulerRepository.save(new ScheduleDay(dayOfWeek, user.getUserUUID())));

        TimeStamp timeStamp = new TimeStamp(
                type
        );

        timeStamp.setScheduleDay(day);
        timeStamp = timeStampRepository.save(timeStamp);

        return new ResponseEntity<>(timeStamp, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            final int timeStampId
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        TimeStamp timeStamp = timeStampRepository.findById(timeStampId).orElseThrow();

        ScheduleDay day = timeStamp.getScheduleDay();

        if (!day.getUserUUID().equals(user.getUserUUID())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (day.getDayOfWeek() != dayOfWeek) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        timeStampRepository.delete(timeStamp);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<ScheduleDay>> getAllScheduleDays(
            final @NotNull UUID sessionID
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        List<ScheduleDay> days = schedulerRepository.findByUserUUID(user.getUserUUID());
        return new ResponseEntity<>(days, HttpStatus.OK);
    }
}
