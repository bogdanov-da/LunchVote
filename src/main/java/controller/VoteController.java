package controller;

import model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.RestaurantRepository;
import repository.UserRepository;
import repository.VoteRepository;
import to.Mapper;
import to.VoteTo;

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
    public VoteTo getCurrent(@PathVariable int userId) {
        return mapper.toTo(voteRepository.getByDate(LocalDate.now(), userId).orElseThrow());
    }

    @GetMapping("/{userId}")
    public List<VoteTo> getAll(@PathVariable int userId) {
        return voteRepository.getByUser(userId).stream().map(mapper :: toTo).toList();
    }

    @PostMapping
    public Vote create(@RequestParam int userId, @RequestParam int restaurantId) {
        Vote vote = new Vote(userRepository.getReferenceById(userId), restaurantRepository.getReferenceById(restaurantId), LocalDate.now());
        voteRepository.save(vote);
        return vote;
    }

    @PutMapping
    public void update(@RequestParam int userId, @RequestParam int restaurantId) {
        Vote vote = voteRepository.getByDate(LocalDate.now(), userId).orElseThrow();
        if(LocalTime.now(Clock.systemDefaultZone()).isBefore(LocalTime.of(11, 0))) {
            vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        } else throw new RuntimeException();
    }
}
