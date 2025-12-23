package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    @Query("SELECT a FROM Assignment a WHERE a.studentUUID = :studentUUID")
    Optional<List<Assignment>> findAssignmentsByStudentUUID(final String studentUUID);

    Optional<Assignment> findAssignmentByStudentUUIDAndId(final String studentUUID, final int id);

}