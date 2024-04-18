package org.bda.voteapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_restaurant_date_idx")})
public class Menu extends AbstractBaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dish> dishes;

    @NotNull
    @Column(name = "date", nullable = false)
    private final LocalDate date = LocalDate.now();

    public Menu() {
    }

    public Menu(Integer id, List<Dish> dishes, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        setDishes(dishes);
    }

    public Menu(Integer id, List<Dish> dishes) {
        super(id);
        this.restaurant = null;
        setDishes(dishes);
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
        dishes.forEach(dish -> dish.setMenu(this));
        this.dishes = dishes.isEmpty() ? List.of() : new ArrayList<>(dishes);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurant=" + restaurant +
                ", dishes=" + dishes +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
