package org.bda.voteapp.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class DishTo extends NamedTo {
    private Integer price;
    private Integer menuId;

    public DishTo(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
        this.menuId = null;
    }

    public DishTo(Integer id, String name, Integer price, int menuId) {
        super(id, name);
        this.price = price;
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return Objects.equals(price, dishTo.price) && Objects.equals(menuId, dishTo.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, menuId);
    }
}
