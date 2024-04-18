package org.bda.voteapp.to;

import org.bda.voteapp.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    public RestaurantTo toTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public Restaurant toModel(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public MenuTo toTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDishes(), menu.getRestaurant().getId());
    }

    public Menu toModel(MenuTo menuTo) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        return new Menu(menuTo.getId(), menuTo.getDishes(), restaurant);
    }

    public VoteTo toToFull(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getDate());
    }

    public VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getId());
    }

    public List<Dish> toDishes(List<DishTo> dishToList) {
        return dishToList.stream().map(dishTo -> new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice())).toList();
    }

    public List<DishTo> toDishesTo(List<Dish> dishes) {
        return dishes.stream().map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice())).toList();
    }

    public static User toModel(UserTo userTo) {
        return new User(userTo.getName(), userTo.getEmail(), userTo.getPassword(), userTo.getRoles());
    }

    public static UserTo toTo(User user) {
        return new UserTo(user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}
