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
        return new Menu(null, menuTo.getDishes());
    }

    public VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getDate());
    }

    public Vote toModel(int voteId, User user, Restaurant restaurant) {
        return new Vote(voteId, user, restaurant);
    }

    public List<Dish> toDishes(List<DishTo> dishToList) {
        return dishToList.stream().map(dishTo -> new Dish(null, dishTo.getName(), dishTo.getPrice())).toList();
    }
}
