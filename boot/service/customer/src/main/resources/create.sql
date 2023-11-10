CREATE TABLE IF NOT EXISTS customer_entity
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(256),
    nickname VARCHAR(256),
    name     VARCHAR(256),
    status   VARCHAR(256),
    birth    DATE,
    created  DATE,
    updated  DATE
);

/*
INSERT INTO customer_entity (username, password) VALUES ('John', 'Doe');
*/
