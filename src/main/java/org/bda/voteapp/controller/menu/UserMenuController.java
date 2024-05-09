package org.bda.voteapp.controller.menu;

import org.bda.voteapp.controller.BaseController;
import org.bda.voteapp.controller.restaurant.UserRestaurantController;
import org.bda.voteapp.model.Menu;
import org.bda.voteapp.repository.MenuRepository;
import org.bda.voteapp.to.MenuTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CacheConfig(cacheNames = "menus")
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController extends BaseController {
    private final MenuRepository menuRepository;

    @Autowired
    public UserMenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/menus/today")
    @Cacheable
    public List<MenuTo> getTodayMenus() {
        List<Menu> menus = menuRepository.getAllByLocalDate(LocalDate.now());
        log.info("Get today menus");
        return menus.stream().map(mapper::toTo).toList();
    }
}
