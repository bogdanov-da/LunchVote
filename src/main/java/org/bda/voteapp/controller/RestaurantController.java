package org.bda.voteapp.controller;

import org.bda.voteapp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.bda.voteapp.util.ValidationUtil.assureIdConsistent;
import static org.bda.voteapp.util.ValidationUtil.checkNew;

@RestController
@CacheConfig(cacheNames="restaurants")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends BaseController {
    public static final String REST_URL = "/api/v1/restaurants";
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository) {
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
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        assureIdConsistent(restaurant, id);
        log.info("Get restaurant by id = {}", id);
        return restaurant;
    }

    @GetMapping("/of-day")
    public Restaurant get() {
        Restaurant restaurant = restaurantRepository.getMaxVotedRestaurant(LocalDate.now()).orElseThrow();
        log.info("Get restaurant of the day by max votes");
        return restaurant;
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        log.info("Create new restaurant {}", restaurant);
        return created;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@RequestParam String name, @PathVariable int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        assureIdConsistent(restaurant, id);
        restaurant.setName(name);
        log.info("Restaurant {} was updated by name {}", id, name);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant by id = {}", id);
        restaurantRepository.deleteById(id);
    }
}
