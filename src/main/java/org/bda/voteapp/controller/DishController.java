package org.bda.voteapp.controller;

import org.bda.voteapp.model.Dish;
import org.bda.voteapp.repository.DishRepository;
import org.bda.voteapp.to.DishTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController extends BaseController {
    private final DishRepository repository;

    @Autowired
    public DishController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/menus/{menuId}/dishes")
    @Cacheable("menus")
    public List<DishTo> getAll(@PathVariable int menuId) {
        log.info("Get all dishes by menu id = {}", menuId);
        List<Dish> dishes = repository.getAllByMenuId(menuId);
        return mapper.toDishesTo(dishes);
    }
}
