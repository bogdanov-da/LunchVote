package model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {
    private User user;
    private Restaurant restaurant;
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDate date) {
        this(null, user, restaurant, date);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate date) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
