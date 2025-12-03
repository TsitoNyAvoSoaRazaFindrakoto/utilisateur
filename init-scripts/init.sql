DO $$ 
BEGIN 
  IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'utilisateur') THEN 
    CREATE DATABASE utilisateur;
  END IF;
END $$;

\c utilisateur;


-- Créer les tables si elles n'existent pas
CREATE TABLE IF NOT EXISTS role (
    id_role SERIAL PRIMARY KEY,
    role VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS utilisateur (
    id_utilisateur SERIAL PRIMARY KEY,
    pseudo VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    id_role INTEGER NOT NULL REFERENCES role(id_role)
);

CREATE TABLE IF NOT EXISTS token (
    id_token SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    date_expiration TIMESTAMP NOT NULL,
    date_creation TIMESTAMP NOT NULL,
    id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE IF NOT EXISTS pin (
    id_pin SERIAL PRIMARY KEY,
    pin VARCHAR(255) NOT NULL,
    date_expiration TIMESTAMP NOT NULL,
    date_creation TIMESTAMP NOT NULL,
    id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE IF NOT EXISTS tentative_connection (
    id_tentative_connection SERIAL PRIMARY KEY,
    nombre INTEGER NOT NULL,
    date_tentative TIMESTAMP NOT NULL,
    id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE IF NOT EXISTS inscription (
    id_inscription SERIAL PRIMARY KEY,
    pseudo VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS token_inscription (
    id_token_inscription SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    date_expiration TIMESTAMP NOT NULL,
    date_creation TIMESTAMP NOT NULL,
    id_inscription INTEGER NOT NULL REFERENCES inscription(id_inscription)
);

-- Éviter d'insérer des doublons dans la table `role`
INSERT INTO role(role) VALUES ('Membre simple') ON CONFLICT(role) DO NOTHING;
INSERT INTO role(role) VALUES ('Admin') ON CONFLICT(role) DO NOTHING;


-- itu16 io mot de passe io
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(1,'Lizka','LizkaRyan626@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(2,'Rebeca','Rebeca@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(3,'Darwin','Darwin@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(4,'John','John@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(5,'Doe','Doe@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(6,'Flight','Flight@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(7,'Amy','Amy@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(8,'Light','Light@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(9,'Bolivia','Bolivia@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',1) ON CONFLICT(email) DO NOTHING;
insert into utilisateur(id_utilisateur,pseudo,email,password,id_role) values(10,'Admin','admin@gmail.com','ea624c9233a63ef464f23b1c3201e44a70e57463908a9440c0e408885237ab67',2) ON CONFLICT(email) DO NOTHING;