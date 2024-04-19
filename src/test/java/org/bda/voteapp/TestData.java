package org.bda.voteapp;

import org.bda.voteapp.config.AppSecurityConfigurer;
import org.bda.voteapp.model.Dish;
import org.bda.voteapp.model.Menu;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.model.User;
import org.bda.voteapp.to.DishTo;
import org.bda.voteapp.to.MenuTo;
import org.bda.voteapp.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bda.voteapp.model.Role.*;

public class TestData {
    public static final String ADMIN_ROLE = "admin";
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class);
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant.name");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final User admin = AppSecurityConfigurer.admin;/*new User(100001, "Admin", "admin@gmail.com", "admin_password",
            LocalDateTime.of(2020, 1, 1, 0, 0, 0), List.of(ADMIN, USER));*/
    public static final User user = AppSecurityConfigurer.user;/*new User(100000, "User", "user@gmail.com", "user_password",
            LocalDateTime.of(2022, 1, 1, 0, 0, 0), Collections.singleton(USER));*/
    public static final User guest = new User(100002, "Guest", "guest@gmail.com", "guest_password",
            LocalDateTime.of(2023, 12, 31, 12, 59, 0));
    public static final User newUser = new User("New one", "new@gmail.com", "new_password", USER);

    public static final Restaurant restaurant1 = new Restaurant(100003, "Вкусно и почка");
    public static final Restaurant restaurant2 = new Restaurant(100004, "McDonalds");
    public static final Restaurant restaurant3 = new Restaurant(100005, "ООО Ирина-2000");
    public static final Restaurant restaurant4 = new Restaurant(100006, "Вечерняя Караганда");
    public static final Restaurant newRestaurant = new Restaurant(null, "New one");

    public static final DishTo dish1 = new DishTo(null, "Бульон на воде", 555.55);
    public static final DishTo dish2 = new DishTo(null, "Шашлык-машлык", 1000.00);
    public static final Dish dish3 = new Dish(100012, "Пюре с котлеткой", 333.99);
    public static final Dish dish4 = new Dish(100013, "Каша гречневая", 1000.01);
    public static final Dish dish5 = new Dish(100014, "Картофель фри", 77.11);

    public static final Menu menu1 = new Menu(100010, new ArrayList<>(), null);
    public static final Menu menu2 = new Menu(100011, new ArrayList<>(), null);
    public static final MenuTo menuTo = new MenuTo(100007, List.of(dish3, dish4, dish5), 100003);

    public static final MenuTo newMenu = new MenuTo(null, new ArrayList<>(), null);

    public static final VoteTo vote1 = new VoteTo(100022, 100003, 100000, LocalDate.of(2023, 12, 30));
    public static final VoteTo vote2 = new VoteTo(100023, 100003, 100000, LocalDate.of(2023, 12, 31));
    public static VoteTo newVote = new VoteTo(null, 100004, 100000, LocalDate.now());


}
