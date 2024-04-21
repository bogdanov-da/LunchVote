package org.bda.voteapp.controller;

import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.model.User;
import org.bda.voteapp.model.Vote;
import org.bda.voteapp.util.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.repository.VoteRepository;
import org.bda.voteapp.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.bda.voteapp.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends BaseController {

    public static final String REST_URL = "/api/v1/votes";
    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @Cacheable("votes")
    public VoteTo get(@PathVariable int id) {
        Vote vote = voteRepository.findById(id).orElseThrow();
        log.info("Get vote by id = {}", id);
        return mapper.toTo(vote);
    }

    @GetMapping("/by-user")
    public List<VoteTo> getByUserId(@RequestParam int id) {
        List<Vote> votes = voteRepository.getByUserId(id);
        log.info("Get votes by userId = {}", id);
        return votes.stream().map(mapper::toTo).toList();
    }

    @PostMapping
    @CacheEvict(value = "votes", allEntries = true)
    public VoteTo create(@RequestParam int userId, @RequestParam int restaurantId) {
        User user = userRepository.findById(userId).orElseThrow();
        assureIdConsistent(user, userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        assureIdConsistent(restaurant, restaurantId);
        Vote vote = new Vote(user, restaurant);
        Vote created = voteRepository.save(vote);
        vote.setId(created.getId());
        log.info("Vote by user {} for restaurant {}", userId, restaurantId);
        return mapper.toTo(vote);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete vote by id {}", id);
        voteRepository.deleteById(id);
    }

    @PutMapping
    @CacheEvict(value = "votes", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int userId, @RequestParam int restaurantId) {
        updateForTest(userId, restaurantId, LocalTime.now());
    }

    @PutMapping("/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int userId, @RequestParam int restaurantId, @RequestParam LocalTime localTime) {
        updateForTest(userId, restaurantId, localTime);
    }

    private void updateForTest(int userId, int restaurantId, LocalTime localTime) {
        Vote vote = voteRepository.getByDateAndUserId(LocalDate.now(), userId).orElseThrow();
        if (localTime.isBefore(LocalTime.of(11, 0))) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
            assureIdConsistent(restaurant, restaurantId);
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
            log.info("Update vote for user {} on restaurant {}", userId, restaurantId);
        } else {
            log.debug("User {} tried vote in time {}", userId, localTime);
            throw new IllegalRequestDataException("It's too late for change your opinion. Let's try tomorrow.");
        }
    }
}
