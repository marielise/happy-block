FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/happy-block-*.jar happy-block.jar
RUN mkdir /wallets
# Expose port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "happy-block.jar"]
