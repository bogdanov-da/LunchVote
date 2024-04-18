package org.bda.voteapp.controller;

import org.bda.voteapp.model.Menu;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.to.DishTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.MenuRepository;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends BaseController {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/{restaurantId}/menus")
    public MenuTo create(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);
        log.info("Create menu for restaurant {}", restaurantId);
        return mapper.toTo(created);
    }

    @PostMapping("/{restaurantId}/menus/{menuId}")
    public MenuTo addDishes(@RequestBody List<DishTo> dishesTo, @PathVariable int restaurantId, @PathVariable int menuId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        menu.setRestaurant(restaurant);
        menu.setDishes(mapper.toDishes(dishesTo));
        Menu created = menuRepository.save(menu);
        log.info("Add dishes to menu {} for restaurant {}", menuId, restaurantId);
        return mapper.toTo(created);
    }

    @GetMapping("/{restaurantId}/menus")
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        List<Menu> menus = menuRepository.getAllByRestaurantId(restaurantId);
        log.info("Get all menus for restaurant {}", restaurantId);
        return menus.stream().map(mapper::toTo).toList();
    }

    @GetMapping("/menus/{id}")
    public MenuTo get(@PathVariable int id) {
        Menu menu = menuRepository.findById(id).orElseThrow();
        log.info("Get menu by id = {}", id);
        return mapper.toTo(menu);
    }

    @GetMapping("/{restaurantId}/menus/today")
    public MenuTo getTodayMenu(@PathVariable int restaurantId) {
        Menu menu = menuRepository.getByDateAndRestaurantId(LocalDate.now(), restaurantId).orElseThrow();
        log.info("Get today menu for restaurant {}", restaurantId);
        return mapper.toTo(menu);
    }

    @DeleteMapping("/{restaurantId}/menus")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllForRestaurant(@PathVariable int restaurantId) {
        List<Menu> menus = menuRepository.getAllByRestaurantId(restaurantId);
        log.info("Delete all menus for restaurant {}", restaurantId);
        menuRepository.deleteAll(menus);
    }
}
