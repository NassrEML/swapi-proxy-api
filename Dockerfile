FROM openjdk:21-jdk

WORKDIR /app

COPY . /app

CMD ["java", "-jar", "swapi-proxy-api-app/target/swapi-proxy-api-app-1.0.0-SNAPSHOT.jar"]