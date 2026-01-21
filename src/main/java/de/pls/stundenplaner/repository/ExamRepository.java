package de.pls.stundenplaner.repository;

import de.pls.stundenplaner.model.ExamEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Integer> {

    @Query("SELECT e FROM ExamEntity e WHERE e.userUUID = :userUUID")
    List<ExamEntity> findExamsByUserUUID(final @NotNull UUID userUUID);

}