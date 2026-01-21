package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Integer> {

    @Query("SELECT a FROM AssignmentEntity a WHERE a.userUUID = :userUUID")
    List<AssignmentEntity> findAssignmentsByUserUUID(final UUID userUUID);

}