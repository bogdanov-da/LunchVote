package model;

public class Dish extends AbstractNameEntity {
    private double price;

    public Dish() {
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
