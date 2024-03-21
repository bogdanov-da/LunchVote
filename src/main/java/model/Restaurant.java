package model;

import java.util.List;

public class Restaurant extends AbstractNameEntity {
    private List<Menu> menus;

    public Restaurant() {
    }

    public Restaurant(String name, List<Menu> menus) {
        this(null, name, menus);
    }

    public Restaurant(Integer id, String name, List<Menu> menus) {
        super(id, name);
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
