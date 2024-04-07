package org.bda.voteapp.repository;

import org.bda.voteapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
   // @Query("SELECT v FROM Vote v WHERE v.date = :date AND v.user.id = :userId")
    Optional<Vote> getByDateAndUserId(LocalDate date, int userId);

   List<Vote> getByUserId(int userId);
}
