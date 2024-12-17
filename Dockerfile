# Étape 1 : build (optionnel si vous avez déjà votre JAR localement)
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/utilisateur-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
