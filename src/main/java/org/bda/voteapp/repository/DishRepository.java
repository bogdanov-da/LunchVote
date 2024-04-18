package org.bda.voteapp.repository;

import org.bda.voteapp.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends CrudRepository<Dish, Integer> {
    List<Dish> getAllByMenuId(int menuId);
}
