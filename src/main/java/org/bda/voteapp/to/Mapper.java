package org.bda.voteapp.to;

import org.bda.voteapp.model.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class Mapper {
    public MenuTo toTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDishes(), menu.getRestaurant().getId(), menu.getLocalDate());
    }

    public Menu toModel(MenuTo menuTo) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(menuTo.getRestaurantId());
        return new Menu(menuTo.getId(), menuTo.getDishes(), restaurant, menuTo.getDate());
    }

    public VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getUser().getId(), vote.getDate());
    }

    public List<Dish> toDishes(List<DishTo> dishToList) {
        return dishToList.stream().map(dishTo -> new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice())).toList();
    }

    public List<DishTo> toDishesTo(List<Dish> dishes) {
        return dishes.stream().map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice(), dish.getMenu().getId())).toList();
    }

    public static User toModel(UserTo userTo) {
        return new User(userTo.getName(), userTo.getEmail(), userTo.getPassword(), Collections.singleton(Role.USER));
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
