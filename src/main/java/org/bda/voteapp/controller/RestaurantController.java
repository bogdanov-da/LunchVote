package org.bda.voteapp.controller;

import org.bda.voteapp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.to.Mapper;
import org.bda.voteapp.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final Mapper mapper;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository, Mapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        return restaurantRepository.findAll().stream().map(mapper :: toTo).toList();
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return mapper.toTo(restaurantRepository.getReferenceById(id));
    }

    @PostMapping
    public Restaurant create(@RequestBody RestaurantTo restaurantTo) {
        Restaurant restaurant = mapper.toModel(restaurantTo);
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        Restaurant restaurant = mapper.toModel(restaurantTo);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        restaurantRepository.deleteById(id);
    }
}
