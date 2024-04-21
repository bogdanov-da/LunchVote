INSERT INTO users (name, email, password, registered)
VALUES ('User', 'user@gmail.com', 'user_password', '2022-01-01 00:00:00'),
       ('Admin', 'admin@gmail.com', 'admin_password', '2020-01-01 00:00:00'),
       ('Guest', 'guest@gmail.com', 'guest_password', '2023-12-31 12:59:00');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurant (name)
VALUES ('Вкусно и почка'),
       ('McDonalds'),
       ('ООО Ирина-2000'),
       ('Вечерняя Караганда');

INSERT INTO menu (restaurant_id, date)
VALUES (100003, CURRENT_DATE),
       (100004, CURRENT_DATE),
       (100005, CURRENT_DATE),
       (100006, '2023-12-30'),
       (100006, '2023-12-31');

INSERT INTO dish (menu_id, name, price)
VALUES (100007, 'Пюре с котлеткой', 333.99),
       (100007, 'Каша гречневая', 1000.01),
       (100007, 'Картофель фри', 77.11),
       (100008, 'Суши селедочные', 999.99),
       (100008, 'Чизкейк', 10.00),
       (100008, 'Салат Оливье', 40.77),
       (100008, 'Компотик', 123.45),
       (100009, 'Шашлык', 40.80),
       (100009, 'Сахарок', 9.45),
       (100009, 'Шардоне', 54.32);

INSERT INTO vote (user_id, restaurant_id, date)
VALUES (100000, 100003, '2023-12-30'),
       (100000, 100003, '2023-12-31'),
       (100001, 100004, CURRENT_DATE),
       (100002, 100005, '2023-12-31');



