package model;

import java.time.LocalDate;
import java.util.List;

public class Menu extends AbstractBaseEntity {
    private Restaurant restaurant;
    private List<Dish> dishes;
    private final LocalDate date = LocalDate.now();

    public Menu() {
    }

    public Menu(Integer id, Restaurant restaurant, List<Dish> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }
}
