package to;

import model.Menu;
import model.Restaurant;
import model.Vote;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public RestaurantTo toTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public Restaurant toModel(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public MenuTo toTo(Menu menu) {
        return new MenuTo(menu.getDishes());
    }

    public Menu toModel(MenuTo menuTo) {
        return new Menu(menuTo.getDishes());
    }

    public VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getRestaurant().getId(), vote.getDate());
    }

    public Vote toModel(VoteTo voteTo) {
        return new Vote();
    }
}
