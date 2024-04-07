package org.bda.voteapp.to;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bda.voteapp.model.Dish;

import java.util.List;

public class MenuTo {
    private int id;
    @NotEmpty
    @Valid
    private List<Dish> dishes;

    @NotNull
    private int restaurantId;

    public MenuTo(int id, List<Dish> dishes, int restaurantId) {
        this.id = id;
        this.dishes = dishes;
        this.restaurantId = restaurantId;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
