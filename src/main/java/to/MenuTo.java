package to;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import model.Dish;

import java.util.List;

public class MenuTo {
    @NotEmpty
    @Valid
    private List<Dish> dishes;

    public MenuTo() {
    }

    public MenuTo(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
