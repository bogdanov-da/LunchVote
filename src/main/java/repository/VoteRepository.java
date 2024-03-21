package repository;

import model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> getByUser(int userId);

    Optional<Vote> getByDate(LocalDate date, int userId);
}
