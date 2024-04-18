package org.bda.voteapp.controller.user;

import org.bda.voteapp.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ProfileUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileUserController extends AbstractUserController {
    public static final String REST_URL = "/api/v1/profile";

    public ProfileUserController(UserRepository repository) {
        super(repository);
    }
}
