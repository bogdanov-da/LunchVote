package org.bda.voteapp.controller;

import org.bda.voteapp.to.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    protected final Mapper mapper = new Mapper();
    protected final Logger log = LoggerFactory.getLogger(getClass());
}
