package org.bda.voteapp.repository;

import org.bda.voteapp.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends CrudRepository<Dish, Integer> {
    List<Dish> getAllByMenuId(int menuId);
}
