package org.bda.voteapp.repository;

import org.bda.voteapp.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.id = (SELECT v.restaurant.id FROM Vote v WHERE v.date = ?1 " +
            "GROUP BY v.restaurant.id ORDER BY count (v.restaurant.id) DESC LIMIT 1)")
    Optional<Restaurant> getMaxVotedRestaurant(LocalDate date);
}
