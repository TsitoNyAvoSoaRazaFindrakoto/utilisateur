\c
postgres;
drop
DATABASE if EXISTS utilisateur;

create
database utilisateur;
\c
utilisateur;

CREATE TABLE role
(
    id_role SERIAL,
    role    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_role),
    UNIQUE (role)
);

CREATE TABLE utilisateur
(
    id_utilisateur SERIAL,
    pseudo         VARCHAR(50)  NOT NULL,
    email          VARCHAR(100) NOT NULL,
    password       VARCHAR(255) NOT NULL,
    id_role        INTEGER      NOT NULL,
    PRIMARY KEY (id_utilisateur),
    UNIQUE (email),
    FOREIGN KEY (id_role) REFERENCES role (id_role)
);

CREATE TABLE token
(
    id_token        SERIAL,
    token           VARCHAR(255) NOT NULL,
    date_expiration TIMESTAMP    NOT NULL,
    date_creation   TIMESTAMP    NOT NULL,
    id_utilisateur  INTEGER      NOT NULL,
    PRIMARY KEY (id_token),
    UNIQUE (token),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id_utilisateur)
);

CREATE TABLE pin
(
    id_pin          SERIAL,
    pin             VARCHAR(255) NOT NULL,
    date_expiration TIMESTAMP    NOT NULL,
    date_creation   TIMESTAMP    NOT NULL,
    id_utilisateur  INTEGER      NOT NULL,
    PRIMARY KEY (id_pin),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id_utilisateur)
);

CREATE TABLE tentative_connection
(
    id_tentative_connection SERIAL,
    nombre                  INTEGER   NOT NULL,
    date_tentative          TIMESTAMP NOT NULL,
    id_utilisateur          INTEGER   NOT NULL,
    PRIMARY KEY (id_tentative_connection),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id_utilisateur)
);
