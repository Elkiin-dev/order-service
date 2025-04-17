# image
FROM eclipse-temurin:17-jdk-alpine

# Dir in container
WORKDIR /app

# Copy Jar compiled
COPY build/libs/order-service-*.jar order-service.jar

# Port to expose
EXPOSE 8080

# Execute app
ENTRYPOINT ["java", "-jar", "order-service.jar"]