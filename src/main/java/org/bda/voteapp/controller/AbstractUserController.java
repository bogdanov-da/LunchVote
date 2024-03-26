package org.bda.voteapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bda.voteapp.repository.UserRepository;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private UserRepository repository;



}
