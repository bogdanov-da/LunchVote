package org.bda.voteapp.controller.user;

import org.bda.voteapp.controller.BaseController;
import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class AbstractUserController extends BaseController {
    final UserRepository repository;

    @Autowired
    public AbstractUserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("Get user by id = {}", id);
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam String email) {
        log.info("Get user by email = {}", email);
        return repository.getByEmail(email).orElseThrow();
    }
}
