package org.bda.voteapp.repository;

import org.bda.voteapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> getByEmail(String email);

    List<User> getAllByRegisteredBetween(LocalDateTime from, LocalDateTime to);
}
