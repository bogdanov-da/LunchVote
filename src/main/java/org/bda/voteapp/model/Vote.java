package org.bda.voteapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "local_date"}, name = "vote_unique_user_date_idx")})
public class Vote extends AbstractBaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "local_date", nullable = false)
    private LocalDate date;

    public Vote(User user, Restaurant restaurant) {
        this(null, user, restaurant);
    }

    public Vote(Integer id, User user, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", restaurant=" + restaurant +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
