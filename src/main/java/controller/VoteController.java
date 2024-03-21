package controller;

import model.Vote;
import repository.RestaurantRepository;
import repository.UserRepository;
import repository.VoteRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VoteController {
    private VoteRepository voteRepository;

    private RestaurantRepository restaurantRepository;

    private UserRepository userRepository;

    public Vote getCurrent(int userId) {
        return voteRepository.getByDate(LocalDate.now(), userId).orElseThrow();
    }

    public List<Vote> getByUser(int userId) {
        return voteRepository.getByUser(userId);
    }

    public Vote create(int userId, int restaurantId) {
        Vote vote = new Vote(userRepository.getReferenceById(userId), restaurantRepository.getReferenceById(restaurantId), LocalDate.now());
        voteRepository.save(vote);
        return vote;
    }

    public void update(int userId, int restaurantId) {
        Vote vote = voteRepository.getByDate(LocalDate.now(), userId).orElseThrow();
        if(LocalTime.now(Clock.systemDefaultZone()).isBefore(LocalTime.of(11, 0))) {
            vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        } else throw new RuntimeException();
    }
}
