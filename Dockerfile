FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app

COPY ./target/utilisateur-0.0.1-SNAPSHOT.jar application.jar
COPY ./init-scripts/init.sql .
COPY ./entrypoint.sh .

RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]

EXPOSE 8082

