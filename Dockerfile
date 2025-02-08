# Étape 1 : Construction de l'application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copier uniquement les fichiers de configuration pour bénéficier du cache Maven
COPY pom.xml ./
COPY src ./src

# Télécharger les dépendances en les mettant en cache
RUN mvn dependency:go-offline

# Compiler le projet
RUN mvn clean package -DskipTests

# Étape 2 : Image minimale pour exécuter l'application
FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app

# Copier le JAR compilé depuis l'étape de build
COPY --from=builder /app/target/utilisateur-0.0.1-SNAPSHOT.jar application.jar
COPY init-scripts/init.sql .
COPY entrypoint.sh /entrypoint.sh
COPY .env /app/.env

RUN chmod +x /entrypoint.sh

# Définir le script comme point d'entrée
ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8082
