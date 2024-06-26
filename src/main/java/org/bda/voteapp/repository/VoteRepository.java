package org.bda.voteapp.repository;

import org.bda.voteapp.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends CrudRepository<Vote, Integer> {
    Optional<Vote> getByDateAndUserId(LocalDate date, int userId);

    List<Vote> getByUserId(int userId);

    Optional<Vote> getById(int id);
}
