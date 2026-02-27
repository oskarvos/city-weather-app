INSERT INTO users(last_name, first_name, email, role, login, password)
VALUES ('Ivanov', 'Ivan', 'ivanov@gmail.com', 'USER', 'user',
        crypt('123', gen_salt('bf'))),
       ('Petrov', 'Petr', 'petrov@gmail.com', 'ADMIN', 'admin',
        crypt('123', gen_salt('bf')))
;