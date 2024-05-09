Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote for a restaurant they want to have lunch at today
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

Swagger-ui: http://localhost:8080/swagger-ui/index.html  
Admin: admin@gmail.com / admin  
User: user@gmail.com / user  

Admin can create new restaurant, change restaurant name, add menu, add dishes to menu, create new user, get users with filters, votes.
User can get restaurant list, menu, vote for menu, get info about user by id or email.