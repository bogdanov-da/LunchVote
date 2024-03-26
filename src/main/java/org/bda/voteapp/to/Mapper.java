package org.bda.voteapp.to;

import org.bda.voteapp.model.Dish;
import org.bda.voteapp.model.Menu;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.model.Vote;
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
        return new Menu(null, menuTo.getDishes());
    }

    public VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getRestaurant().getId(), vote.getDate());
    }

    public Vote toModel(VoteTo voteTo) {
        return new Vote();
    }

    public List<Dish> toDishes(List<DishTo> dishToList) {
        return dishToList.stream().map(dishTo -> new Dish(null, dishTo.getName(), dishTo.getPrice())).toList();
    }
}
