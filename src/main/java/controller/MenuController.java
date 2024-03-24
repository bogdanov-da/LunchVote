package controller;

import model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.MenuRepository;
import repository.RestaurantRepository;
import to.Mapper;
import to.MenuTo;

import java.time.LocalDate;

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
    public MenuTo create(@RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        Menu menu = new Menu();
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        menuRepository.save(menu);
        return mapper.toTo(menu);
    }

    @PutMapping
    public void update(@RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        Menu menu = menuRepository.getByDate(restaurantId, LocalDate.now()).orElseThrow();
        menu.setDishes(menuTo.getDishes());
        menuRepository.save(menu);
    }

    @GetMapping("/{restaurantId}")
    public MenuTo get(@PathVariable int restaurantId) {
        return mapper.toTo(menuRepository.getByDate(restaurantId, LocalDate.now()).orElseThrow());
    }

    @DeleteMapping("/{restaurantId}")
    public void delete(@PathVariable int restaurantId) {
        Menu menu = menuRepository.getByDate(restaurantId, LocalDate.now()).orElseThrow();
        menuRepository.delete(menu);
    }
}
