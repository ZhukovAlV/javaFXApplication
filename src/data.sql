CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    accesLvl BIGINT,
    dateOfCreation TIMESTAMP,
    dateOfModification DATE
    )

INSERT INTO user (id, login, password, accesLvl, dateOfCreation, dateOfModification)
VALUES
(1, 'kant', 'kant', 1, NOW(), NOW()),
(2, 'gegel', 'gegel', 1, NOW(), NOW())

SELECT *
FROM user