CREATE TABLE role(
   id_role SERIAL,
   role VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_role),
   UNIQUE(role)
);

CREATE TABLE token(
   id_token SERIAL,
   token VARCHAR(255)  NOT NULL,
   date_expiration TIMESTAMP NOT NULL,
   PRIMARY KEY(id_token),
   UNIQUE(token)
);

CREATE TABLE pin(
   id_pin SERIAL,
   pin VARCHAR(5)  NOT NULL,
   PRIMARY KEY(id_pin)
);

CREATE TABLE utilisateur(
   id_utilisateur SERIAL,
   pseudo VARCHAR(50)  NOT NULL,
   email VARCHAR(100)  NOT NULL,
   password VARCHAR(255)  NOT NULL,
   id_pin INTEGER,
   id_token INTEGER,
   id_role INTEGER NOT NULL,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email),
   FOREIGN KEY(id_pin) REFERENCES pin(id_pin),
   FOREIGN KEY(id_token) REFERENCES token(id_token),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);
