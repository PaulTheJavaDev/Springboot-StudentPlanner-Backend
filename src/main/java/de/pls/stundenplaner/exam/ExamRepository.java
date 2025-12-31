package de.pls.stundenplaner.exam;

import de.pls.stundenplaner.exam.model.Exam;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("SELECT e FROM Exam e WHERE e.userUUID = :userUUID")
    List<Exam> findExamsByUserUUID(final @NotNull UUID userUUID);

    Optional<Exam> findExamByUserUUIDAndId(final @NotNull UUID userUUID, final int id);

}