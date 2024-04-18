package org.bda.voteapp.to;

import java.util.Objects;

public class DishTo {
    private Integer id;
    private String name;
    private Double price;
    private Integer menuId;

    public DishTo() {
    }

    public DishTo(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return Objects.equals(id, dishTo.id) && Objects.equals(name, dishTo.name) && Objects.equals(price, dishTo.price) && Objects.equals(menuId, dishTo.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, menuId);
    }
}
