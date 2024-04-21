package org.bda.voteapp.to;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bda.voteapp.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuTo extends BaseTo {
    @NotEmpty
    @Valid
    private List<Dish> dishes;

    @NotNull
    private Integer restaurantId;

    public MenuTo() {
        super(null);
    }

    public MenuTo(Integer id, List<Dish> dishes, Integer restaurantId) {
        super(id);
        this.dishes = dishes == null ? new ArrayList<>() : dishes;
        this.restaurantId = restaurantId;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes == null ? new ArrayList<>() : dishes;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return Objects.equals(id, menuTo.id) && Objects.equals(dishes, menuTo.dishes) && Objects.equals(restaurantId, menuTo.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishes, restaurantId);
    }
}
