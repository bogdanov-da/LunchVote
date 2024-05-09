package org.bda.voteapp.controller.restaurant;

import org.bda.voteapp.controller.BaseController;
import org.bda.voteapp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;

import java.util.List;

import static org.bda.voteapp.util.ValidationUtil.checkNotFound;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends BaseController {
    public static final String REST_URL = "/api/v1/restaurants";
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserRestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        log.info("Get all restaurants");
        return restaurants;
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        Restaurant restaurant = checkNotFound(restaurantRepository.findById(id), id);
        log.info("Get restaurant by id = {}", id);
        return restaurant;
    }
}
