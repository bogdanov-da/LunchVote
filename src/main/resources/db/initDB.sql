DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR                                  NOT NULL,
    email      VARCHAR                                  NOT NULL,
    password   VARCHAR                                  NOT NULL,
    enabled    BOOL                DEFAULT TRUE         NOT NULL,
    registered TIMESTAMP           DEFAULT CURRENT_DATE NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurant (name);

CREATE TABLE menu
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER                                  NOT NULL,
    date          DATE                DEFAULT CURRENT_DATE NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_unique_restaurant_date_idx ON menu (restaurant_id, date);

CREATE TABLE dish
(
    menu_id INTEGER NOT NULL,
    name    VARCHAR NOT NULL,
    price   INTEGER NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dish_unique_menu_id_name_idx ON dish (menu_id, name);

CREATE TABLE vote
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER                                  NOT NULL,
    restaurant_id INTEGER                                  NOT NULL,
    date          DATE                DEFAULT CURRENT_DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_date_idx ON vote (user_id, date);