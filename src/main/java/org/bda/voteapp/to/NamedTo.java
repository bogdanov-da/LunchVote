package org.bda.voteapp.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NamedTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
