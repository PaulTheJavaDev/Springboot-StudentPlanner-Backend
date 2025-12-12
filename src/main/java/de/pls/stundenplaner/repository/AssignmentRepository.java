package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByStudentUUID(UUID studentUUID);

    Assignment findAssignmentByStudentUUIDAndId(UUID studentUUID, int id);

    UUID studentUUID(UUID studentUUID);
}