package org.bda.voteapp.repository;

import org.bda.voteapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.bda.voteapp.config.AppSecurityConfigurer.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Integer> {
    @Transactional
    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    Optional<User> getByEmail(String email);

    User getById(Integer id);

    List<User> getAllByRegisteredBetween(LocalDateTime from, LocalDateTime to);
}
