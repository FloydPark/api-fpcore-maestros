FROM openjdk:8-alpine

ENV MONGO_DB_HOST="localhost"
ENV MONGO_DB_PORT="27017"
COPY build/libs/*.jar api.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar api.jar --spring.data.mongodb.host=$MONGO_DB_HOST --spring.data.mongodb.port=$MONGO_DB_PORT" ]