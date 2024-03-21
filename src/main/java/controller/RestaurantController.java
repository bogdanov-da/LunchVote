package controller;

import model.Menu;
import model.Restaurant;
import repository.RestaurantRepository;

import java.util.List;

public class RestaurantController {
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant get(int restaurantId) {
        return restaurantRepository.getReferenceById(restaurantId);
    }

    public Restaurant create(String name, List<Menu> menus) {
        Restaurant restaurant = new Restaurant(name, menus);
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }
}
