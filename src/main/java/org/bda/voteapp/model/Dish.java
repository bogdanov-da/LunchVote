package org.bda.voteapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
@Table(name = "dish")
public class Dish extends AbstractNameEntity {
    @Column(name = "price", nullable = false)
    @Positive
    @NotNull
    private double price;

    @ManyToOne
    @JsonBackReference
    private Menu menu;

    public Dish() {
    }

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, double price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
