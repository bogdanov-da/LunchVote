package org.bda.voteapp.controller;

import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.bda.voteapp.util.DateTimeUtil.atStartOfDayOrMin;
import static org.bda.voteapp.util.DateTimeUtil.atStartOfNextDayOrMax;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return repository.save(user);
    }

    @GetMapping
    public List<User> getAll(@RequestParam @Nullable LocalDate dateFrom, @RequestParam @Nullable LocalDate dateTo) {
        return repository.getAllByRegisteredBetween(atStartOfDayOrMin(dateFrom), atStartOfNextDayOrMax(dateTo));
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam String email) {
        return repository.getByEmail(email).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }
}
