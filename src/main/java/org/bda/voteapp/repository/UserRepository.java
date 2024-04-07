package org.bda.voteapp.repository;

import org.bda.voteapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByEmail(String email);

    List<User> getAllByRegisteredBetween(LocalDateTime from, LocalDateTime to);
}
