package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "dish")
public class Dish extends AbstractNameEntity {
    @Column(name = "price", nullable = false)
    @Positive
    @NotNull
    private double price;

    public Dish() {
    }

    public Dish(String name, double price) {
        this(null, name, price);
    }

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
