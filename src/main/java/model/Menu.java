package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_restaurant_date_idx")})
public class Menu extends AbstractBaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @NotEmpty
    @Valid
   // @CollectionTable(name = "dish", joinColumns = @JoinColumn(name = "menu_id"),
   //         uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "dish_unique_menu_id_name_idx")})
    //@ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @JoinTable(name = "dish",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
  //  @ManyToMany(fetch = FetchType.EAGER)
    @OneToMany
    private List<Dish> dishes;

    @NotNull
    @Column(name = "date", nullable = false)
    private final LocalDate date = LocalDate.now();

    public Menu() {
    }

    public Menu(List<Dish> dishes) {
        this(null, null, dishes);
    }

    public Menu(Restaurant restaurant, List<Dish> dishes) {
        this(null, restaurant, dishes);
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
