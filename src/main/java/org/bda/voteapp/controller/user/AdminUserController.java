package org.bda.voteapp.controller.user;

import org.bda.voteapp.controller.BaseController;
import org.bda.voteapp.exception.NotFoundException;
import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.bda.voteapp.to.Mapper.toModel;
import static org.bda.voteapp.util.DateTimeUtil.atStartOfDayOrMin;
import static org.bda.voteapp.util.DateTimeUtil.atStartOfNextDayOrMax;
import static org.bda.voteapp.util.ValidationUtil.checkNotFound;

@RestController
@CacheConfig(cacheNames = "users")
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends BaseController {
    public static final String REST_URL = "/api/v1/admin/users";

    private final UserRepository repository;

    @Autowired
    public AdminUserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("Get user by id = {}", id);
        return checkNotFound(repository.findById(id), id);
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam String email) {
        Optional<User> user = repository.getByEmail(email);
        if(user.isEmpty()) throw new NotFoundException("User with email " + email + " not found");
        log.info("Get user by email = {}", email);
        return user.get();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserTo userTo) {
        log.info("Create user with body {}", userTo);
        User created = repository.save(toModel(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<User> getAll(@RequestParam @Nullable LocalDate registeredFrom, @RequestParam @Nullable LocalDate registeredTo) {
        log.info("Get all users");
        return repository.getAllByRegisteredBetween(atStartOfDayOrMin(registeredFrom), atStartOfNextDayOrMax(registeredTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete user  by id = {}", id);
        repository.deleteById(id);
    }
}
