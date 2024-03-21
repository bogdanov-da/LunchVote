package controller;

import model.Dish;
import model.Menu;
import org.springframework.web.bind.annotation.RestController;
import repository.MenuRepository;
import repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MenuController {
    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;

    public Menu create(int restaurantId) {
        Menu menu = new Menu();
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        menuRepository.save(menu);
        return menu;
    }

    public void update(int restaurantId, List<Dish> dishes) {
        Menu menu = new Menu(restaurantRepository.getReferenceById(restaurantId), dishes);
        menuRepository.save(menu);
    }

    public Menu get(int restaurantId, LocalDate date) {
        return menuRepository.getByDate(restaurantId, date).orElseThrow();
    }

    public void delete(int id) {
        menuRepository.deleteById(id);
    }
}
