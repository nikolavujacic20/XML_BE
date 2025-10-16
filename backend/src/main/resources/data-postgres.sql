insert into Role (name)
VALUES ('ROLE_GRADJANIN');/*1*/
insert into Role (name)
VALUES ('ROLE_SLUZBENIK');/*2*/
insert into Role (name)
VALUES ('ROLE_USER');

insert into user_auth(deleted, is_enabled)
values (false, true),
       (false, true);

insert into users(email, password, name, surname, phone_number, user_auth_id, role)
values ('g@gmail.com', '$2a$10$tnplXdStY6t7kOqqKssMYedAGjJ0T3OJH2BxeT81c1YrDqOUvHLD6', 'Gradjanin', 'Peric', '069123456', 1, 0),
       ('s@gmail.com', '$2a$10$gyVv5jxxWVZRfUYlcbewoePW1wpaOjwFkolJhhg5fvmeHScQYom0q', 'Sluzbenik', 'Peric', '069123456', 2, 1);

insert into user_auth_roles(user_auth_id, roles_id)
values (1, 1),
       (1, 3),
       (2, 2),
       (2, 3);