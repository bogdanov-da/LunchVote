package org.bda.voteapp.repository;

import org.bda.voteapp.model.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> getAllByRestaurantId(int restaurantId);
    Optional<Menu> getByDateAndRestaurantId(LocalDate date, int restaurantId);
}
