## Utilisation d'une image Maven avec JDK 17 (Eclipse Temurin)
#FROM maven:3.9.6-eclipse-temurin-17 AS app
#
#WORKDIR /app
#
## Copier uniquement pom.xml pour optimiser le cache Maven
#COPY pom.xml ./
#
## Télécharger les dépendances et les stocker dans un volume pour éviter de les retélécharger
#RUN mvn dependency:go-offline
#
## Copier tout le code source
#COPY src ./src
#
## Exposer le port de l'application
#EXPOSE 8082
#
## Définir un volume pour stocker le cache des dépendances Maven
#VOLUME /root/.m2
#
## Lancer la compilation et exécuter l'application directement
#CMD ["mvn", "clean", "spring-boot:run"]
FROM openjdk:17-jdk-slim
WORKDIR /app2

COPY ./target/utilisateur-0.0.1-SNAPSHOT.jar /app2/jarr.jar
COPY ./src/main/resources/keyFirebase.json /app2/src/main/resources/keyFirebase.json

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app2/jarr.jar"]