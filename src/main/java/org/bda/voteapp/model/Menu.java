package org.bda.voteapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "local_date"}, name = "menu_unique_restaurant_date_idx")})
public class Menu extends AbstractBaseEntity {
    @Setter
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
    @Column(name = "local_date", nullable = false)
    private LocalDate localDate;

    public Menu(Integer id, List<Dish> dishes, Restaurant restaurant, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.localDate = localDate;
        setDishes(dishes);
    }

    public Menu(Integer id, List<Dish> dishes) {
        super(id);
        this.restaurant = null;
        setDishes(dishes);
    }

    public void setDishes(List<Dish> dishes) {
        dishes.forEach(dish -> dish.setMenu(this));
        this.dishes = dishes.isEmpty() ? List.of() : new ArrayList<>(dishes);
    }
}
