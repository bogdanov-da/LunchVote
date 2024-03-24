package repository;

import model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> getByUser(int userId);

    @Query("SELECT v FROM Vote v WHERE v.date = :date AND v.user.id = :userId")
    Optional<Vote> getByDate(LocalDate date, int userId);
}
