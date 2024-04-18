package org.bda.voteapp.controller.user;

import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class AbstractUserController {
    final UserRepository repository;
    protected final Logger log = LoggerFactory.getLogger(getClass());

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
