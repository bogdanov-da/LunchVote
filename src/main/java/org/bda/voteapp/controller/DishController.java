package org.bda.voteapp.controller;

import org.bda.voteapp.repository.DishRepository;
import org.bda.voteapp.to.DishTo;
import org.bda.voteapp.to.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    private final DishRepository repository;
    private final Mapper mapper;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public DishController(DishRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/menus/{menuId}/dishes")
    public List<DishTo> getAll(@PathVariable int menuId) {
        log.info("Get all menus by id = {}", menuId);
        return mapper.toDishesTo(repository.getAllByMenuId(menuId));
    }
}
