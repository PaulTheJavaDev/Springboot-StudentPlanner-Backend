package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByUserUUID(UUID userUUID);

    Integer id(int id);
}