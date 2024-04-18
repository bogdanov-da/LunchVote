package org.bda.voteapp.to;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class VoteTo {
    private Integer id;
    private Integer restaurantId;
    private Integer userId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restaurantId, Integer userId, LocalDate date) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return id == voteTo.id && Objects.equals(restaurantId, voteTo.restaurantId) && Objects.equals(userId, voteTo.userId) && Objects.equals(date, voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantId, userId, date);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", userId=" + userId +
                ", date=" + date +
                '}';
    }
}
