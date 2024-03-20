package model;

public abstract class AbstractNameEntity extends AbstractBaseEntity {
    private String name;

    public AbstractNameEntity() {
    }

    public AbstractNameEntity(Integer id, String name) {
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
