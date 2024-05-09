package org.bda.voteapp.repository;

import org.bda.voteapp.model.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> getAllByRestaurantId(int restaurantId);

    List<Menu> getAllByLocalDate(LocalDate date);
}
