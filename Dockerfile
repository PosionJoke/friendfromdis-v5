# Etap budowy
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN ./mvnw -q -B -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -q -B -DskipTests package

# Etap uruchamiania
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
ENV SERVER_PORT=9997
EXPOSE 9997
ENTRYPOINT ["java","-jar","/app/app.jar"]

