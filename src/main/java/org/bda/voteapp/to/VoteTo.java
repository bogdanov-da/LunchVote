package org.bda.voteapp.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class VoteTo extends BaseTo {
    private Integer restaurantId;
    private Integer userId;
    private LocalDate date;

    public VoteTo(Integer id, Integer restaurantId, Integer userId, LocalDate date) {
        super(id);
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(id, voteTo.id) && Objects.equals(restaurantId, voteTo.restaurantId) && Objects.equals(userId, voteTo.userId) && Objects.equals(date, voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantId, userId, date);
    }
}
