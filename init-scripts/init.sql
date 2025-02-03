drop DATABASE if EXISTS utilisateur;

create database utilisateur;
\c utilisateur;

CREATE TABLE role
(
    id_role SERIAL,
    role    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_role),
    UNIQUE (role)
);

CREATE TABLE utilisateur
(
    id_utilisateur INTEGER,
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


insert into role(role) values   ('Membre simple'),
                                ('Admin');

insert into tentative_connection(nombre) values(0);

-- itu16 io mot de passe io
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(2,'Fifah','valeafifaliana@gmail.com','$2a$10$UXHGTlWb27E1kXYQMsNzyOc1Fb5fzI3w31d2Sm5aC9b0JGSov12PC',1);

