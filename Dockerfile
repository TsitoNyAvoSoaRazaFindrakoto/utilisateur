# Utilisation directe de l'image Maven avec JDK 17
FROM maven:3.9.6-jdk-17 AS app

WORKDIR /app

# Copier uniquement pom.xml pour optimiser le cache Maven
COPY pom.xml ./

# Télécharger les dépendances et les stocker dans un volume pour éviter de les retélécharger
RUN mvn dependency:go-offline

# Copier tout le code source
COPY src ./src

# Exposer le port de l'application
EXPOSE 8082

# Définir un volume pour stocker le cache des dépendances Maven
VOLUME /root/.m2

# Lancer la compilation et exécuter l'application directement
CMD ["mvn", "clean", "spring-boot:run"]
