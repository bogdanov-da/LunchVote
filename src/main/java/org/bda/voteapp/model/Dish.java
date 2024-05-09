package org.bda.voteapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "dish")
public class Dish extends AbstractNameEntity {
    @Column(name = "price", nullable = false)
    @Positive
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
