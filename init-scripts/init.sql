CREATE TABLE IF NOT EXISTS ROLE(
    ID_ROLE SERIAL,
    ROLE VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID_ROLE),
    UNIQUE(ROLE)
);

CREATE TABLE IF NOT EXISTS TOKEN(
    ID_TOKEN SERIAL,
    TOKEN VARCHAR(255) NOT NULL,
    DATE_EXPIRATION TIMESTAMP NOT NULL,
    PRIMARY KEY(ID_TOKEN),
    UNIQUE(TOKEN)
);

CREATE TABLE IF NOT EXISTS PIN(
    ID_PIN SERIAL,
    PIN VARCHAR(255) NOT NULL,
    DATE_EXPIRATION TIMESTAMP NOT NULL,
    PRIMARY KEY(ID_PIN)
);

CREATE TABLE IF NOT EXISTS TENTATIVE_CONNECTION(
    ID_TENTATIVE_CONNECTION SERIAL,
    NOMBRE INTEGER NOT NULL,
    PRIMARY KEY(ID_TENTATIVE_CONNECTION)
);

CREATE TABLE IF NOT EXISTS UTILISATEUR(
    ID_UTILISATEUR SERIAL,
    PSEUDO VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    ID_TENTATIVE_CONNECTION INTEGER NOT NULL,
    ID_PIN INTEGER,
    ID_TOKEN INTEGER,
    ID_ROLE INTEGER NOT NULL,
    PRIMARY KEY(ID_UTILISATEUR),
    UNIQUE(ID_TENTATIVE_CONNECTION),
    UNIQUE(EMAIL),
    FOREIGN KEY(ID_TENTATIVE_CONNECTION) REFERENCES TENTATIVE_CONNECTION(ID_TENTATIVE_CONNECTION),
    FOREIGN KEY(ID_PIN) REFERENCES PIN(ID_PIN),
    FOREIGN KEY(ID_TOKEN) REFERENCES TOKEN(ID_TOKEN),
    FOREIGN KEY(ID_ROLE) REFERENCES ROLE(ID_ROLE)
);

-- Insérer des rôles
INSERT INTO role (role) VALUES 
('Admin'),
('User'),
('Moderator');

-- Insérer des tokens
INSERT INTO token (token, date_expiration) VALUES 
('token12345', '2025-01-15 12:00:00'),
('token67890', '2025-02-01 15:00:00'),
('token11223', '2025-01-20 10:00:00'),
('token44556', '2025-02-05 08:30:00'),
('token77889', '2025-01-25 11:45:00');

-- Insérer des PINs
INSERT INTO pin (pin, date_expiration) VALUES 
('1234', '2025-01-12 23:59:59'),
('5678', '2025-01-20 23:59:59'),
('91011', '2025-02-01 23:59:59'),
('121314', '2025-01-25 23:59:59'),
('151617', '2025-02-10 23:59:59');

-- Insérer des tentatives de connexion
INSERT INTO tentative_connection (nombre) VALUES 
(0),
(1),
(2),
(3),
(4);

-- Insérer des utilisateurs
INSERT INTO utilisateur (pseudo, email, password, id_tentative_connection, id_pin, id_token, id_role) VALUES 
('user1', 'user1@example.com', 'password1', 1, 1, 1, 2),
('user2', 'user2@example.com', 'password2', 2, 2, 2, 2),
('user3', 'user3@example.com', 'password3', 3, 3, 3, 2),
('user4', 'user4@example.com', 'password4', 4, 4, 4, 2),
('user5', 'user5@example.com', 'password5', 5, 5, 5, 2),
('user6', 'user6@example.com', 'password6', 1, NULL, NULL, 2),
('user7', 'user7@example.com', 'password7', 2, NULL, NULL, 2),
('user8', 'user8@example.com', 'password8', 3, NULL, NULL, 2),
('user9', 'user9@example.com', 'password9', 4, NULL, NULL, 2),
('user10', 'user10@example.com', 'password10', 5, NULL, NULL, 2);
