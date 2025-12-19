package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    @Query("SELECT a FROM Assignment a WHERE a.studentUUID = :studentUUID")
    List<Assignment> findAssignmentsByStudentUUID(final String studentUUID);

    Assignment findAssignmentByStudentUUIDAndId(final String studentUUID, final int id);

}