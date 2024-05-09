package org.bda.voteapp.controller.menu;

import org.bda.voteapp.controller.BaseController;
import org.bda.voteapp.controller.restaurant.AdminRestaurantController;
import org.bda.voteapp.model.Menu;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.to.DishTo;
import org.bda.voteapp.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.MenuRepository;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.to.MenuTo;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.bda.voteapp.util.ValidationUtil.checkNotFound;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController extends BaseController {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public AdminMenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/{restaurantId}/menu")
    @Transactional
    public ResponseEntity<MenuTo> create(@PathVariable int restaurantId, @RequestBody MenuTo menuTo) {
        Menu created = menuRepository.save(mapper.toModel(menuTo));
        log.info("Create menu for restaurant {}", restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(AdminRestaurantController.REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toTo(created));
    }

    @PostMapping("/{restaurantId}/menus/{menuId}/dishes")
    @Transactional
    public ResponseEntity<MenuTo> addDishes(@RequestBody List<DishTo> dishesTo, @PathVariable int restaurantId, @PathVariable int menuId) {
        Restaurant restaurant = checkNotFound(restaurantRepository.findById(restaurantId), restaurantId);
        Menu menu = checkNotFound(menuRepository.findById(menuId), menuId);
        dishesTo.forEach(ValidationUtil::checkNew);
        menu.setRestaurant(restaurant);
        menu.setDishes(mapper.toDishes(dishesTo));
        Menu created = menuRepository.save(menu);
        log.info("Add dishes to menu {} for restaurant {}", menuId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(AdminRestaurantController.REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toTo(created));
    }

    @GetMapping("/{restaurantId}/menus")
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        List<Menu> menus = menuRepository.getAllByRestaurantId(restaurantId);
        log.info("Get all menus for restaurant {}", restaurantId);
        return menus.stream().map(mapper::toTo).toList();
    }

    @GetMapping("/menus/{id}")
    public MenuTo get(@PathVariable int id) {
        Menu menu = checkNotFound(menuRepository.findById(id), id);
        log.info("Get menu by id = {}", id);
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
