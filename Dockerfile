# Utiliser l'image Eclipse Temurin pour Java 17
FROM eclipse-temurin:17-jdk

# Créer un répertoire pour l'application
WORKDIR /app

# Copier le fichier JAR de l'application dans l'image
COPY target/utilisateur-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 8082

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
