package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.UserRepository;

import java.util.List;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private UserRepository repository;



}
