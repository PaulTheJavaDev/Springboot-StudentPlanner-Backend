package de.pls.stundenplaner.service;

import de.pls.stundenplaner.dto.request.scheduler.CreateTimeStampRequest;
import de.pls.stundenplaner.dto.request.scheduler.UpdateTimeStampRequest;
import de.pls.stundenplaner.model.DayOfWeek;
import de.pls.stundenplaner.model.ScheduleDay;
import de.pls.stundenplaner.model.TimeStamp;
import de.pls.stundenplaner.repository.SchedulerRepository;
import de.pls.stundenplaner.repository.TimeStampRepository;
import de.pls.stundenplaner.model.User;
import de.pls.stundenplaner.util.exceptions.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static de.pls.stundenplaner.util.model.UserUtil.checkUserExistenceBySessionID;

@Service
public class SchedulerService {

    private final TimeStampRepository timeStampRepository;
    private final SchedulerRepository schedulerRepository;

    public SchedulerService(TimeStampRepository timeStampRepository,
                            SchedulerRepository schedulerRepository) {
        this.timeStampRepository = timeStampRepository;
        this.schedulerRepository = schedulerRepository;
    }

    public TimeStamp updateTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            final int timeStampId,
            UpdateTimeStampRequest request
    ) {
        User user = checkUserExistenceBySessionID(sessionID);
        TimeStamp timeStamp = timeStampRepository.findById(timeStampId)
                .orElseThrow(EntityNotFoundException::new);

        validateUserOwnership(timeStamp, user.getUserUUID(), dayOfWeek);

        timeStamp.setType(request.getType());
        timeStamp.setText(request.getText());

        return timeStampRepository.save(timeStamp);
    }

    public TimeStamp createTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            CreateTimeStampRequest request
    ) {
        User user = checkUserExistenceBySessionID(sessionID);

        ScheduleDay day = schedulerRepository
                .findByUserUUIDAndDayOfWeek(user.getUserUUID(), dayOfWeek)
                .orElseGet(() -> schedulerRepository.save(
                        new ScheduleDay(dayOfWeek, user.getUserUUID())
                ));

        TimeStamp timeStamp = new TimeStamp(request.getType());
        timeStamp.setScheduleDay(day);

        timeStamp.setText(request.getText());

        timeStampRepository.save(timeStamp);

        return timeStamp;
    }

    public void deleteTimeStamp(
            final @NotNull UUID sessionID,
            final @NotNull DayOfWeek dayOfWeek,
            final int timeStampId
    ) {
        User user = checkUserExistenceBySessionID(sessionID);
        TimeStamp timeStamp = timeStampRepository.findById(timeStampId)
                .orElseThrow(EntityNotFoundException::new);

        validateUserOwnership(timeStamp, user.getUserUUID(), dayOfWeek);

        timeStampRepository.delete(timeStamp);
    }

    public List<ScheduleDay> getAllScheduleDays(final @NotNull UUID sessionID) {
        User user = checkUserExistenceBySessionID(sessionID);
        return schedulerRepository.findByUserUUID(user.getUserUUID());
    }

    private void validateUserOwnership(TimeStamp timeStamp, UUID userUUID, DayOfWeek dayOfWeek) {
        ScheduleDay day = timeStamp.getScheduleDay();

        if (!day.getUserUUID().equals(userUUID)) {
            throw new UnauthorizedException();
        }

        if (day.getDayOfWeek() != dayOfWeek) {
            throw new EntityNotFoundException();
        }
    }
}