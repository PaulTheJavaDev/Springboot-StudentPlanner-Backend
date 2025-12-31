package de.pls.stundenplaner.assignment;

import de.pls.stundenplaner.assignment.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    @Query("SELECT a FROM Assignment a WHERE a.userUUID = :userUUID")
    Optional<List<Assignment>> findAssignmentsByStudentUUID(final UUID userUUID);

    Optional<Assignment> findAssignmentByUserUUIDAndId(final UUID studentUUID, final int id);

}