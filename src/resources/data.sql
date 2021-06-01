DROP TABLE user;

CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    accesLvl BIGINT,
    dateOfCreation TIMESTAMP,
    dateOfModification TIMESTAMP
    )

INSERT INTO user (id, login, password, accesLvl, dateOfCreation, dateOfModification)
VALUES
(1, 'kant', 'kant', 1, NOW(), NOW()),
(2, 'gegel', 'gegel', 1, NOW(), NOW())

SELECT *
FROM user


DROP TABLE access_level;

CREATE TABLE access_level (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL
    )

INSERT INTO access_level (id, title)
VALUES
(1, 'Admin'),
(2, 'Tester'),
(3, 'User')

SELECT *
FROM access_level