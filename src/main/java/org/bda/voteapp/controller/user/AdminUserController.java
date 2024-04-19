package org.bda.voteapp.controller.user;

import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.to.UserTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.bda.voteapp.to.Mapper.toModel;
import static org.bda.voteapp.util.DateTimeUtil.atStartOfDayOrMin;
import static org.bda.voteapp.util.DateTimeUtil.atStartOfNextDayOrMax;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {
    public static final String REST_URL = "/api/v1/admin/users";

    public AdminUserController(UserRepository repository) {
        super(repository);
    }

    @PostMapping
    public User create(@RequestBody UserTo userTo) {
        log.info("Create user with body {}", userTo);
        User created = repository.save(toModel(userTo));
        return created;
    }

    @GetMapping
    public List<User> getAll(@RequestParam @Nullable LocalDate dateFrom, @RequestParam @Nullable LocalDate dateTo) {
        log.info("Get all users");
        return repository.getAllByRegisteredBetween(atStartOfDayOrMin(dateFrom), atStartOfNextDayOrMax(dateTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete user  by id = {}", id);
        repository.deleteById(id);
    }
}
