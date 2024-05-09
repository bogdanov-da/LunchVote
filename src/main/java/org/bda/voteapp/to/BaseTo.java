package org.bda.voteapp.to;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bda.voteapp.util.HasId;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTo implements HasId {
    protected Integer id;

    @Hidden
    public Integer getId() {
        return id;
    }
}
