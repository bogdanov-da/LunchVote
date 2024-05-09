package org.bda.voteapp.repository;

import org.bda.voteapp.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
