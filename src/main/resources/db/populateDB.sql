INSERT INTO users (name, email, password, registered)
VALUES ('User', 'user@gmail.com', '{noop}user', '2022-01-01 00:00:00'),
       ('Admin', 'admin@gmail.com', '{noop}admin', '2020-01-01 00:00:00'),
       ('Guest', 'guest@gmail.com', '{noop}guest', '2023-12-31 12:59:00'),
       ('Phil', 'phil@gmail.com', '{noop}phil', '2022-01-01 01:00:00');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 4);

INSERT INTO restaurant (name)
VALUES ('Вкусно и почка'),
       ('McDonalds'),
       ('ООО Ирина-2000'),
       ('Вечерняя Караганда');

INSERT INTO menu (restaurant_id, local_date)
VALUES (1, CURRENT_DATE),
       (2, '2025-01-01'),
       (3, '2025-01-01'),
       (4, '2023-12-30'),
       (4, '2023-12-31');

INSERT INTO dish (menu_id, name, price)
VALUES (1, 'Пюре с котлеткой', 333),
       (1, 'Каша гречневая', 1000),
       (1, 'Картофель фри', 77),
       (2, 'Суши селедочные', 999),
       (2, 'Чизкейк', 10),
       (2, 'Салат Оливье', 40),
       (2, 'Компотик', 123),
       (3, 'Шашлык', 40),
       (3, 'Сахарок', 9),
       (3, 'Шардоне', 54);

INSERT INTO vote (user_id, restaurant_id, local_date)
VALUES (1, 1, '2023-12-30'),
       (1, 1, '2023-12-31'),
       (2, 2, CURRENT_DATE),
       (4, 2, CURRENT_DATE);



