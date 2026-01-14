package de.pls.stundenplaner.scheduler;

import de.pls.stundenplaner.scheduler.model.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Integer> {
}
