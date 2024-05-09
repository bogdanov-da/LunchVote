package org.bda.voteapp.controller;

import org.bda.voteapp.model.AuthUser;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.model.User;
import org.bda.voteapp.model.Vote;
import org.bda.voteapp.exception.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.repository.VoteRepository;
import org.bda.voteapp.to.VoteTo;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.bda.voteapp.util.DateTimeUtil.isBeforeTime;
import static org.bda.voteapp.util.ValidationUtil.checkNotFound;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends BaseController {

    public static final String REST_URL = "/api/v1/profile/vote";
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id) {
        Vote vote = checkNotFound(voteRepository.getById(id), id);
        log.info("Get vote by id {}", id);
        return mapper.toTo(vote);
    }

    @GetMapping("/today")
    public VoteTo getByUserToday(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        Vote vote = checkNotFound(voteRepository.getByDateAndUserId(LocalDate.now(), userId), userId);
        log.info("Get votes today");
        return mapper.toTo(vote);
    }

    @GetMapping("/all-votes")
    public List<VoteTo> getAllByUser(@AuthenticationPrincipal AuthUser authUser) {
        List<Vote> votes = voteRepository.getByUserId(authUser.id());
        log.info("Get all votes");
        return votes.stream().map(mapper::toTo).toList();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VoteTo> create(@RequestBody Restaurant restaurant, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        if (voteRepository.getByDateAndUserId(LocalDate.now(), userId).isPresent())
            throw new IllegalRequestDataException("User voted today");
        User user = checkNotFound(userRepository.findById(userId), userId);
        Vote vote = new Vote(user, restaurant);
        Vote created = voteRepository.save(vote);
        vote.setId(created.getId());
        log.info("Vote by user {} for restaurant {}", userId, restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toTo(vote));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody Restaurant restaurant, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        if (isBeforeTime()) {
            Vote vote = checkNotFound(voteRepository.getByDateAndUserId(LocalDate.now(), userId), userId);
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
            log.info("Update vote for user {} on restaurant {}", userId, restaurant.getId());
        } else {
            log.debug("User {} tried vote in time", userId);
            throw new IllegalRequestDataException("It's too late for change your opinion. Let's try tomorrow.");
        }
    }
}
