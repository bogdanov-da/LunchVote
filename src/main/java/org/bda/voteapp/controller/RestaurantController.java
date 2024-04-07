package org.bda.voteapp.controller;

import org.bda.voteapp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return restaurantRepository.getReferenceById(id);
    }

    @GetMapping("/of-day")
    public Restaurant get() {
        return restaurantRepository.getMaxVotedRestaurant(LocalDate.now()).orElseThrow();
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/{id}")
    public void update(@RequestParam String name, @PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        restaurant.setName(name);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        restaurantRepository.deleteById(id);
    }
}
