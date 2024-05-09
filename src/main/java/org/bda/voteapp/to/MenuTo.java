package org.bda.voteapp.to;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bda.voteapp.model.Dish;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class MenuTo extends BaseTo {
    @NotEmpty
    @Valid
    private List<Dish> dishes;

    @Setter
    @NotNull
    private Integer restaurantId;

    @NotNull
    private LocalDate date;

    public MenuTo(Integer id, List<Dish> dishes, Integer restaurantId, LocalDate date) {
        super(id);
        this.dishes = dishes == null ? new ArrayList<>() : dishes;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return Objects.equals(dishes, menuTo.dishes) && Objects.equals(restaurantId, menuTo.restaurantId) && Objects.equals(date, menuTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishes, restaurantId, date);
    }
}
