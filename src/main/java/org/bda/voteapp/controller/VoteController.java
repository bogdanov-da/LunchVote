package org.bda.voteapp.controller;

import org.bda.voteapp.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bda.voteapp.repository.RestaurantRepository;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.repository.VoteRepository;
import org.bda.voteapp.to.Mapper;
import org.bda.voteapp.to.VoteTo;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;
    private final Mapper mapper;

    @Autowired
    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, Mapper mapper) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    public List<VoteTo> getByUserId(@PathVariable int userId) {
        return voteRepository.getByUserId(userId).stream().map(mapper::toTo).toList();
    }

    @PostMapping
    public VoteTo create(@RequestParam int userId, @RequestParam int restaurantId) {
        Vote vote = new Vote(userRepository.getReferenceById(userId), restaurantRepository.getReferenceById(restaurantId));
        voteRepository.save(vote);
        return mapper.toTo(vote);
    }

    @PutMapping
    public void update(@RequestParam int userId, @RequestParam int restaurantId) {
        Vote vote = voteRepository.getByDateAndUserId(LocalDate.now(), userId).orElseThrow();
        if (LocalTime.now(Clock.systemDefaultZone()).isBefore(LocalTime.of(11, 0))) {
            vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
            voteRepository.save(vote);
        } else throw new RuntimeException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        voteRepository.deleteById(id);
    }
}
