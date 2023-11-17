FROM openjdk:8

COPY . /app

WORKDIR /app

VOLUME /server/Server.jar

CMD ["java", "-jar", "./server/Server.jar"]
