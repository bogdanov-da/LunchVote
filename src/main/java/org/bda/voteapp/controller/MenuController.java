package org.bda.voteapp.controller;

import org.bda.voteapp.model.Menu;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.to.DishTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.MenuRepository;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.to.Mapper;
import org.bda.voteapp.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menus")
public class MenuController {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final Mapper mapper;

    @Autowired
    public MenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository, Mapper mapper) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @PostMapping
    public MenuTo create(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);
        return mapper.toTo(created);
    }

    @PostMapping("/{menuId}")
    public MenuTo addDishes(@RequestBody List<DishTo> dishesTo, @PathVariable int restaurantId, @PathVariable int menuId) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Menu menu = menuRepository.getReferenceById(menuId);
        menu.setRestaurant(restaurant);
        menu.setDishes(mapper.toDishes(dishesTo));
        Menu created = menuRepository.save(menu);
        return mapper.toTo(created);
    }

    @GetMapping
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        return menuRepository.getAllByRestaurantId(restaurantId).stream().map(mapper::toTo).toList();
    }

    @GetMapping("/today")
    public MenuTo getTodayMenu(@PathVariable int restaurantId) {
        Menu menu = menuRepository.getByDateAndRestaurantId(LocalDate.now(), restaurantId).orElseThrow();
        return mapper.toTo(menu);
    }

    @DeleteMapping
    public void deleteAllForRestaurant(@PathVariable int restaurantId) {
        List<Menu> menus = menuRepository.getAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
    }
}
