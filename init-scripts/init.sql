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


-- Insérer un utilisateur avec un mot de passe haché
INSERT INTO utilisateur(pseudo, email, password, id_role) 
VALUES ('Fifah', 'valeafifaliana@gmail.com', '$2a$10$UXHGTlWb27E1kXYQMsNzyOc1Fb5fzI3w31d2Sm5aC9b0JGSov12PC', 1)
ON CONFLICT(email) DO NOTHING;  -- Éviter les doublons