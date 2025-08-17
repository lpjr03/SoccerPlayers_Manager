FROM maven:3.9-amazoncorretto-17-alpine AS build

WORKDIR /app

RUN apk update && apk add --no-cache git
RUN git clone https://github.com/lpjr03/SoccerPlayers_Manager.git progetto && cd progetto && mvn clean install

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build /app/progetto/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]